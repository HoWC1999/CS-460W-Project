package com.tennisclub.controller;

import com.tennisclub.dto.CourtReservationDTO;
import com.tennisclub.model.CourtReservation;
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

  /**
   * Fetch this user’s reservations.
   * GET /api/reservations/my?userId=123
   */
  @GetMapping("/my")
  public ResponseEntity<?> getMyReservations(@RequestParam("userId") int userId) {
    try {
      List<CourtReservation> list = reservationService.getReservationsForUserId(userId);
      return ResponseEntity.ok(list);
    } catch (Exception e) {
      return ResponseEntity
        .badRequest()
        .body("Unable to fetch reservations: " + e.getMessage());
    }
  }

  /**
   * ADMIN ONLY: List all reservations.
   * GET /api/reservations/all
   */
  @GetMapping("/all")
  public ResponseEntity<?> getAllReservations() {
    try {
      List<CourtReservation> all = reservationService.getAllReservations();
      return ResponseEntity.ok(all);
    } catch (Exception e) {
      return ResponseEntity
        .status(500)
        .body("Failed to fetch all reservations: " + e.getMessage());
    }
  }

  /**
   * Create a new reservation for the currently logged-in user.
   * POST /api/reservations/create
   */
  @PostMapping("/create")
  public ResponseEntity<?> create(@RequestBody CourtReservationDTO dto) {
    try {
      CourtReservation created = reservationService.createReservation(dto);
      return ResponseEntity.ok(created);
    } catch (Exception e) {
      logger.error("Reservation creation failed", e);
      return ResponseEntity
        .badRequest()
        .body("Reservation creation failed: " + e.getMessage());
    }
  }

  /**
   * Cancel (delete) a reservation.
   * Supports both DELETE (RESTful) and POST (for compatibility).
   */
  @DeleteMapping("/cancel/{id}")
  public ResponseEntity<?> deleteCancel(@PathVariable("id") int id) {
    return cancelById(id);
  }

  private ResponseEntity<?> cancelById(int id) {
    try {
      reservationService.cancelReservation(id);
      return ResponseEntity.ok("Reservation cancelled");
    } catch (Exception e) {
      logger.error("Cancellation failed for id {}: {}", id, e.getMessage());
      return ResponseEntity
        .badRequest()
        .body("Cancellation failed: " + e.getMessage());
    }
  }
}
