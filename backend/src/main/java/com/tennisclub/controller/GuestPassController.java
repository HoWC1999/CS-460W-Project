// src/main/java/com/tennisclub/controller/GuestPassController.java
package com.tennisclub.controller;

import com.tennisclub.model.GuestPass;
import com.tennisclub.repository.GuestPassRepository;
import com.tennisclub.service.GuestPassService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/guestpasses")
public class GuestPassController {

  private static final Logger logger = LoggerFactory.getLogger(GuestPassController.class);

  private final GuestPassService guestPassService;

  private final GuestPassRepository guestPassRepository;

  public GuestPassController(GuestPassService guestPassService, GuestPassRepository guestPassRepository) {
    this.guestPassService = guestPassService;
    this.guestPassRepository = guestPassRepository;
  }

  /**
   * Endpoint to purchase a guest pass.
   * Expects a request parameter for the user ID.
   */
  @PostMapping("/purchase")
  public ResponseEntity<?> purchaseGuestPass(@RequestParam int userId) {
    try {
      logger.info("Received guest pass purchase request for user {}", userId);
      GuestPass guestPass = guestPassService.purchaseGuestPass(userId);
      return ResponseEntity.ok(guestPass);
    } catch (Exception e) {
      logger.error("Error purchasing guest pass for user {}: {}", userId, e.getMessage(), e);
      return ResponseEntity.badRequest().body("Guest pass purchase failed: " + e.getMessage());
    }
  }

  /**
   * GET /api/guestpasses/my?userId={userId}
   * Retrieves all guest passes for the given user.
   */
  @GetMapping("/my")
  public ResponseEntity<?> getMyGuestPasses(@RequestParam("userId") int userId) {
    try {
      List<GuestPass> passes = guestPassService.getGuestPassesForUser(userId);
      return ResponseEntity.ok(passes);
    } catch (Exception e) {
      logger.error("Error fetching guest passes for user {}: {}", userId, e.getMessage());
      return ResponseEntity
        .status(500)
        .body("Could not fetch guest passes: " + e.getMessage());
    }
  }
}
