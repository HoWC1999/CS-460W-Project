// src/main/java/com/tennisclub/controller/GuestPassController.java
package com.tennisclub.controller;

import com.tennisclub.model.GuestPass;
import com.tennisclub.repository.GuestPassRepository;
import com.tennisclub.service.GuestPassService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/guestpasses")
public class GuestPassController {

  private static final Logger logger = LoggerFactory.getLogger(GuestPassController.class);

  private final GuestPassService guestPassService;

  private final GuestPassRepository GuestPassRepository;

  public GuestPassController(GuestPassService guestPassService, GuestPassRepository guestPassRepository) {
    this.guestPassService = guestPassService;
    GuestPassRepository = guestPassRepository;
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
}
