package com.tennisclub.service;

import com.tennisclub.dto.CourtReservationDTO;
import com.tennisclub.model.CourtReservation;
import com.tennisclub.model.User;
import com.tennisclub.repository.CourtReservationRepository;
import com.tennisclub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Service layer for managing court reservations.
 */
@Service
public class ReservationService {

  @Autowired
  private CourtReservationRepository reservationRepository;

  @Autowired
  private UserRepository userRepository;

  // Default reservation duration in minutes
  private static final int DEFAULT_DURATION_MINUTES = 90;

  private static final SimpleDateFormat DATE_FMT = new SimpleDateFormat("yyyy-MM-dd");

  /**
   * Create a new reservation for a single user.
   *
   * @param dto     contains userId, court, date (yyyy-MM-dd) and time (HH:mm)
   * @return the saved CourtReservation
   * @throws ParseException if date parsing fails
   */
  public CourtReservation createReservation(CourtReservationDTO dto) throws ParseException {
    // 1) Lookup user by ID
    User user = Optional.ofNullable(userRepository.findByUserId(dto.getUserId()))
      .orElseThrow(() -> new RuntimeException("User not found: " + dto.getUserId()));

    // 2) Parse & validate date
    Date reservationDate = DATE_FMT.parse(dto.getDate());
    Date today = DATE_FMT.parse(DATE_FMT.format(new Date()));
    Date maxFuture = new Date(today.getTime() + 60L * 24 * 60 * 60 * 1000);

    if (reservationDate.before(today)) {
      throw new RuntimeException("Reservation date must be in the future.");
    }
    if (reservationDate.after(maxFuture)) {
      throw new RuntimeException("Reservation date cannot be more than 60 days ahead.");
    }

    // 3) Parse & compute times
    String timeStr = dto.getTime();
    if (timeStr.split(":").length == 2) {
      timeStr += ":00";
    }
    Time startTime = Time.valueOf(timeStr);
    Time endTime = new Time(startTime.getTime() + DEFAULT_DURATION_MINUTES * 60 * 1000);

    // 4) Overlap check
    List<CourtReservation> existing =
      reservationRepository.findByReservationDateAndCourtNumber(reservationDate, dto.getCourt());
    for (CourtReservation ex : existing) {
      if (timesOverlap(startTime, endTime, ex.getStartTime(), ex.getEndTime())) {
        throw new RuntimeException(
          "Court " + dto.getCourt() + " is already reserved at that time."
        );
      }
    }

    // 5) Save
    CourtReservation reservation = new CourtReservation();
    reservation.setBookedBy(user);
    reservation.setCourtNumber(dto.getCourt());
    reservation.setReservationDate(reservationDate);
    reservation.setStartTime(startTime);
    reservation.setEndTime(endTime);
    return reservationRepository.save(reservation);
  }

  /**
   * Checks if two time intervals overlap.
   */
  private boolean timesOverlap(Time s1, Time e1, Time s2, Time e2) {
    return s1.before(e2) && s2.before(e1);
  }

  /**
   * Fetch reservations belonging to a single user.
   */
  public List<CourtReservation> getReservationsForUserId(int userId) {
    User user = Optional.ofNullable(userRepository.findByUserId(userId))
      .orElseThrow(() -> new RuntimeException("User not found: " + userId));
    return reservationRepository.findByBookedBy(Optional.of(user));
  }

  /**
   * ADMIN ONLY: Fetch all reservations.
   */
  public List<CourtReservation> getAllReservations() {
    return reservationRepository.findAll();
  }

  /**
   * ADMIN ONLY: Cancel (delete) a reservation by ID.
   *
   * @return true if successfully deleted
   */
  public boolean cancelReservation(int reservationId) {
    if (!reservationRepository.existsById(reservationId)) {
      throw new RuntimeException("Reservation not found: " + reservationId);
    }
    reservationRepository.deleteById(reservationId);
    return true;
  }
}

