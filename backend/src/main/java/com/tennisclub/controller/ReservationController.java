package com.tennisclub.controller;

import com.tennisclub.dto.CourtReservationDTO;
import com.tennisclub.model.CourtReservation;
import com.tennisclub.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

  @Autowired
  private ReservationService reservationService;

  @GetMapping("/my")
  public ResponseEntity<?> getMyReservations(@RequestParam("userId") int userId) {
    try {
      List<CourtReservation> reservations = reservationService.getReservationsForUserId(userId);
      return ResponseEntity.ok(reservations);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Unable to fetch reservations: " + e.getMessage());
    }
  }
}
