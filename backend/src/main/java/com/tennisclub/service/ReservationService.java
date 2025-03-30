package com.tennisclub.service;

import com.tennisclub.model.CourtReservation;
import com.tennisclub.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReservationService {

  @Autowired
  private ReservationRepository reservationRepository;

  public CourtReservation createReservation(CourtReservation reservation) {
    // Here, add any validation and business logic as needed
    return reservationRepository.save(reservation);
  }

  public boolean cancelReservation(int reservationId) {
    if (reservationRepository.existsById(reservationId)) {
      reservationRepository.deleteById(reservationId);
      return true;
    }
    return false;
  }

  public CourtReservation modifyReservation(int reservationId, CourtReservation newReservationData) {
    Optional<CourtReservation> optionalReservation = reservationRepository.findById(reservationId);
    if(optionalReservation.isPresent()){
      CourtReservation reservation = optionalReservation.get();
      reservation.setReservationDate(newReservationData.getReservationDate());
      reservation.setStartTime(newReservationData.getStartTime());
      reservation.setEndTime(newReservationData.getEndTime());
      // Update other fields if necessary
      return reservationRepository.save(reservation);
    }
    throw new RuntimeException("Reservation not found");
  }
}
