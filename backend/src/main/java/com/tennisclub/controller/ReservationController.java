package com.tennisclub.controller;

import com.tennisclub.model.CourtReservation;
import com.tennisclub.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

  @Autowired
  private ReservationService reservationService;

  @PostMapping("/create")
  public ResponseEntity<?> createReservation(@RequestBody CourtReservation reservation) {
    try {
      CourtReservation createdReservation = reservationService.createReservation(reservation);
      return ResponseEntity.ok(createdReservation);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Reservation creation failed: " + e.getMessage());
    }
  }

  @PostMapping("/cancel/{id}")
  public ResponseEntity<?> cancelReservation(@PathVariable int id) {
    try {
      boolean result = reservationService.cancelReservation(id);
      if (result) {
        return ResponseEntity.ok("Reservation cancelled successfully.");
      } else {
        return ResponseEntity.badRequest().body("Cancellation failed.");
      }
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Error: " + e.getMessage());
    }
  }

  @PutMapping("/modify/{id}")
  public ResponseEntity<?> modifyReservation(@PathVariable int id, @RequestBody CourtReservation reservation) {
    try {
      CourtReservation modifiedReservation = reservationService.modifyReservation(id, reservation);
      return ResponseEntity.ok(modifiedReservation);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Modification failed: " + e.getMessage());
    }
  }
}
