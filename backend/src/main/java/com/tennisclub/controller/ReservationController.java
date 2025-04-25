package com.tennisclub.controller;

import com.tennisclub.dto.CourtReservationDTO;
import com.tennisclub.model.CourtReservation;
import com.tennisclub.model.Events;
import com.tennisclub.service.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

  private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);

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
  @PostMapping("/create")
  public ResponseEntity<?> createReservation(@RequestBody CourtReservationDTO reservationDTO) {
    try {
      logger.info("Creating reservation with data: {}", reservationDTO);
      CourtReservation createdReservation = reservationService.createReservation(reservationDTO);
      logger.info("Reservation created successfully: {}", createdReservation);
      return ResponseEntity.ok(createdReservation);
    } catch (Exception e) {
      logger.error("Reservation creation failed: {}", e.getMessage(), e);
      return ResponseEntity.badRequest().body("Reservation creation failed: " + e.getMessage());
    }
  }
}
