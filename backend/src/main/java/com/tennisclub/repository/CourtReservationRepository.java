package com.tennisclub.repository;

import com.tennisclub.model.CourtReservation;
import com.tennisclub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CourtReservationRepository extends JpaRepository<CourtReservation, Integer> {
  List<CourtReservation> findByReservationDate(Date reservationDate);
  List<CourtReservation> findByCourtNumber(int courtNumber);

  // New method to find reservations for a given date and court
  List<CourtReservation> findByReservationDateAndCourtNumber(Date reservationDate, int courtNumber);

  List<CourtReservation> findByBookedBy(User user);
}
