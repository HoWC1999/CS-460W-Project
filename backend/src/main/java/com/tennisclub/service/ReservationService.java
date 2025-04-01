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

@Service
public class ReservationService {

  @Autowired
  private CourtReservationRepository reservationRepository;

  @Autowired
  private UserRepository userRepository;

  // Default reservation duration in minutes (e.g., 90 minutes)
  private static final int DEFAULT_DURATION_MINUTES = 90;

  public CourtReservation createReservation(CourtReservationDTO dto) throws ParseException {
    // Lookup the user by email. (Adjust according to your user handling.)
    User user = userRepository.findByEmail(dto.getEmail());
    if (user == null) {
      throw new RuntimeException("User not found for email: " + dto.getEmail());
    }

    // Parse the reservation date
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date reservationDate = dateFormat.parse(dto.getDate());

    // Parse the start time. Expect "HH:mm" format and append ":00" if needed.
    String timeString = dto.getTime();
    if (timeString.split(":").length == 2) {
      timeString += ":00";
    }
    Time startTime = Time.valueOf(timeString);

    // Calculate the end time using the default duration
    long startMillis = startTime.getTime();
    long durationMillis = DEFAULT_DURATION_MINUTES * 60 * 1000;
    Time endTime = new Time(startMillis + durationMillis);

    // Check for overlapping reservations for the same court and date.
    List<CourtReservation> existingReservations =
      reservationRepository.findByReservationDateAndCourtNumber(reservationDate, dto.getCourt());
    for (CourtReservation existing : existingReservations) {
      if (timesOverlap(startTime, endTime, existing.getStartTime(), existing.getEndTime())) {
        throw new RuntimeException("Court " + dto.getCourt() + " is already reserved during the requested time.");
      }
    }

    // Create the reservation entity and populate it.
    CourtReservation reservation = new CourtReservation();
    reservation.setReservationDate(reservationDate);
    reservation.setStartTime(startTime);
    reservation.setEndTime(endTime);
    reservation.setBookedBy(user);
    reservation.setCourtNumber(dto.getCourt());

    // Save the reservation to the database.
    return reservationRepository.save(reservation);
  }

  /**
   * Helper method to determine if two time intervals overlap.
   * Returns true if [start1, end1) and [start2, end2) overlap.
   */
  private boolean timesOverlap(Time start1, Time end1, Time start2, Time end2) {
    // Two intervals overlap if the start of one is before the end of the other and vice versa.
    return start1.before(end2) && start2.before(end1);
  }
  /**
   * Retrieves reservations for a user based on their user ID.
   *
   * @param userId the user's id
   * @return list of reservations
   */
  public List<CourtReservation> getReservationsForUserId(int userId) {
    User user = userRepository.findById(userId);
    return reservationRepository.findByBookedBy(user);
  }

}
