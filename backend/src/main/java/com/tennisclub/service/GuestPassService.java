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

  private static final double GUEST_PASS_PRICE = 5.00;
  private static final int MAX_GUEST_PASSES_PER_MONTH = 5;

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

    User user = userRepository.findByUserId(userId);
    if (user == null) {
      String errorMsg = "User not found for ID: " + userId;
      logger.error(errorMsg);
      throw new RuntimeException(errorMsg);
    }

    // Enforce monthly limit
    Date now = new Date();

    Calendar startCal = Calendar.getInstance();
    startCal.setTime(now);
    startCal.set(Calendar.DAY_OF_MONTH, 1);
    startCal.set(Calendar.HOUR_OF_DAY, 0);
    startCal.set(Calendar.MINUTE, 0);
    startCal.set(Calendar.SECOND, 0);
    startCal.set(Calendar.MILLISECOND, 0);
    Date monthStart = startCal.getTime();

    Calendar endCal = Calendar.getInstance();
    endCal.setTime(now);
    endCal.set(Calendar.DAY_OF_MONTH, endCal.getActualMaximum(Calendar.DAY_OF_MONTH));
    endCal.set(Calendar.HOUR_OF_DAY, 23);
    endCal.set(Calendar.MINUTE, 59);
    endCal.set(Calendar.SECOND, 59);
    endCal.set(Calendar.MILLISECOND, 999);
    Date monthEnd = endCal.getTime();

    List<GuestPass> passesThisMonth = guestPassRepository.findByUserAndPurchaseDateBetween(user, monthStart, monthEnd);

    if (passesThisMonth.size() >= MAX_GUEST_PASSES_PER_MONTH) {
      logger.warn("User {} has reached the monthly guest pass limit", userId);
      throw new RuntimeException("You have reached the limit of 5 guest passes for this month.");
    }

    // Set expiration to 24 hours from now
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
  /**
   * Returns all guest passes belonging to the given user.
   *
   * @param userId the id of the user
   * @return list of GuestPass entities
   */
  public List<GuestPass> getGuestPassesForUser(int userId) {
    logger.info("Fetching guest passes for user {}", userId);
    return guestPassRepository.findByUser_UserId(userId);
  }
}
