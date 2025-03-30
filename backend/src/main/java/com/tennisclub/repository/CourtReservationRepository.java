package com.tennisclub.repository;

import com.tennisclub.model.CourtReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CourtReservationRepository extends JpaRepository<CourtReservation, Integer> {
  List<CourtReservation> findByReservationDate(Date reservationDate);
  List<CourtReservation> findByCourt_CourtNumber(int courtNumber);
}
