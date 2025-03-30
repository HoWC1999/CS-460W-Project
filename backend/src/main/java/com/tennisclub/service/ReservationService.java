package com.tennisclub.service;

import com.tennisclub.model.Court;
import com.tennisclub.model.CourtReservation;
import com.tennisclub.model.User;
import com.tennisclub.repository.CourtRepository;
import com.tennisclub.repository.CourtReservationRepository;
import com.tennisclub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ReservationService {

  @Autowired
  private CourtReservationRepository reservationRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private CourtRepository courtRepository;

  public CourtReservation createReservation(CourtReservation reservation) {
    // Validate reservation times
    if(!reservation.isValidReservation()) {
      throw new RuntimeException("Invalid reservation times: start time must be before end time");
    }

    // Validate user
    Optional<User> optionalUser = userRepository.findById(reservation.getBookedBy().getUserId());
    if(!optionalUser.isPresent()) {
      throw new RuntimeException("User not found for reservation");
    }
    reservation.setBookedBy(optionalUser.get());

    // Validate court
    Optional<Court> optionalCourt = courtRepository.findById(reservation.getCourt().getCourtNumber());
    if(!optionalCourt.isPresent()) {
      throw new RuntimeException("Court not found");
    }
    Court court = optionalCourt.get();
    if(!court.isAvailable()) {
      throw new RuntimeException("Court is not available for reservation");
    }
    reservation.setCourt(court);

    // Optionally update court availability here

    return reservationRepository.save(reservation);
  }

  public boolean cancelReservation(int reservationId) {
    if(!reservationRepository.existsById(reservationId)) {
      throw new RuntimeException("Reservation not found");
    }
    reservationRepository.deleteById(reservationId);
    // Optionally update court availability here
    return true;
  }

  public CourtReservation modifyReservation(int reservationId, CourtReservation newReservationData) {
    Optional<CourtReservation> optionalReservation = reservationRepository.findById(reservationId);
    if(!optionalReservation.isPresent()) {
      throw new RuntimeException("Reservation not found");
    }
    CourtReservation reservation = optionalReservation.get();
    if(newReservationData.getReservationDate() != null) {
      reservation.setReservationDate(newReservationData.getReservationDate());
    }
    if(newReservationData.getStartTime() != null) {
      reservation.setStartTime(newReservationData.getStartTime());
    }
    if(newReservationData.getEndTime() != null) {
      reservation.setEndTime(newReservationData.getEndTime());
    }
    if(!reservation.isValidReservation()) {
      throw new RuntimeException("Invalid reservation times after modification");
    }
    return reservationRepository.save(reservation);
  }
}
