// src/main/java/com/tennisclub/service/GuestPassService.java
package com.tennisclub.service;

import com.tennisclub.model.GuestPass;
import com.tennisclub.model.User;
import com.tennisclub.repository.GuestPassRepository;
import com.tennisclub.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class GuestPassService {

  private static final Logger logger = LoggerFactory.getLogger(GuestPassService.class);

  // Fixed price for a single-use guest pass.
  private static final double GUEST_PASS_PRICE = 5.00;

  private final GuestPassRepository guestPassRepository;
  private final UserRepository userRepository;

  public GuestPassService(GuestPassRepository guestPassRepository, UserRepository userRepository) {
    this.guestPassRepository = guestPassRepository;
    this.userRepository = userRepository;
  }

  /**
   * Purchases a single-use guest pass for the given user.
   *
   * @param userId The ID of the user purchasing the guest pass.
   * @return The created GuestPass.
   */
  public GuestPass purchaseGuestPass(int userId) {
    logger.info("Attempting to purchase guest pass for user {}", userId);
    User user = userRepository.findById(userId);
    if (user == null) {
      String errorMsg = "User not found for ID: " + userId;
      logger.error(errorMsg);
      throw new RuntimeException(errorMsg);
    }
    Date now = new Date();
    // Set expiration to 24 hours from now.
    Calendar cal = Calendar.getInstance();
    cal.setTime(now);
    cal.add(Calendar.DAY_OF_MONTH, 1);
    Date expirationDate = cal.getTime();

    GuestPass guestPass = new GuestPass();
    guestPass.setUser(user);
    guestPass.setPrice(GUEST_PASS_PRICE);
    guestPass.setPurchaseDate(now);
    guestPass.setExpirationDate(expirationDate);
    guestPass.setUsed(false);

    try {
      GuestPass savedPass = guestPassRepository.save(guestPass);
      logger.info("Guest pass purchased successfully for user {}: {}", userId, savedPass);
      return savedPass;
    } catch (Exception e) {
      logger.error("Error purchasing guest pass for user {}: {}", userId, e.getMessage(), e);
      throw new RuntimeException("Error purchasing guest pass: " + e.getMessage());
    }
  }
  public List<GuestPass> getAllPasses() {
    logger.info("Fetching all guest passes");
    return guestPassRepository.findAll();
  }
}
