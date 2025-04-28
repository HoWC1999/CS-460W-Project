package com.tennisclub.service;

import com.tennisclub.dto.UpdateUserDTO;
import com.tennisclub.model.FinancialTransaction;
import com.tennisclub.model.User;
import com.tennisclub.model.enums.BillingPlan;
import com.tennisclub.model.enums.TransactionStatus;
import com.tennisclub.model.enums.Role;
import com.tennisclub.repository.FinancialTransactionRepository;
import com.tennisclub.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Service class for user-related operations such as registration,
 * profile updates, retrieval, deletion, and role assignment.
 * <p>
 * Automatically generates a pending billing transaction for the
 * initial membership fee upon user registration.
 * </p>
 */
@Service
public class UserService {

  private static final Logger logger = LoggerFactory.getLogger(UserService.class);

  /** Monthly and annual membership fees */
  private static final BigDecimal MONTHLY_FEE = new BigDecimal("50");
  private static final BigDecimal ANNUAL_FEE  = new BigDecimal("400");

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private FinancialTransactionRepository financialTransactionRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  /**
   * Registers a new user, encoding their password and setting defaults.
   * <p>
   * After successful persistence of the user, creates a pending
   * billing transaction for the initial membership fee based on their
   * selected billing plan.
   * </p>
   *
   * @param user the user entity, with raw password and billingPlan set
   * @return the saved user with assigned ID
   * @throws RuntimeException if username already exists or persistence fails
   */
  public User register(User user) {
    logger.info("Registering user: {}", user.getUsername());

    // Check for duplicate username
    if (userRepository.findByUsername(user.getUsername()) != null) {
      logger.error("Registration failed: Username {} already exists", user.getUsername());
      throw new RuntimeException("Username already exists");
    }

    // Encode password
    String rawPassword = user.getPassword();
    user.setPasswordHash(passwordEncoder.encode(rawPassword));
    user.setPassword(null);

    // Set default role and status if missing
    if (user.getRole() == null) {
      user.setRole(Role.MEMBER);
    }
    if (user.getStatus() == null) {
      user.setStatus("Active");
    }

    // Persist user
    User savedUser = userRepository.save(user);
    logger.info("User registered successfully with id: {}", savedUser.getUserId());

    // Create initial pending billing transaction
    BigDecimal fee = (savedUser.getBillingPlan() == BillingPlan.ANNUAL) ? ANNUAL_FEE : MONTHLY_FEE;
    FinancialTransaction tx = new FinancialTransaction();
    tx.setUser(savedUser);
    tx.setAmount(fee);
    tx.setTransactionType(savedUser.getBillingPlan().name().toLowerCase());
    tx.setStatus(TransactionStatus.PENDING);
    tx.setDescription("Initial membership fee");
    tx.setTransactionDate(new Date());
    financialTransactionRepository.save(tx);
    logger.info("Created pending billing transaction for user {}: amount={}", savedUser.getUserId(), fee);

    return savedUser;
  }

  /**
   * Updates profile fields for an existing user. Only non-null
   * fields in the DTO are applied.
   *
   * @param userId     the ID of the user to update
   * @param updateData DTO containing fields to patch
   * @return the updated user
   * @throws RuntimeException if user not found or update fails
   */
  public User updateProfile(int userId, UpdateUserDTO updateData) {
    logger.info("Attempting to update user with id: {}", userId);

    User user = userRepository.findByUserId(userId);
    if (user == null) {
      logger.error("Update failed: No user found with id: {}", userId);
      throw new RuntimeException("User not found");
    }

    // Apply patches
    if (updateData.getUsername() != null) {
      logger.info("Updating username to: {}", updateData.getUsername());
      user.setUsername(updateData.getUsername());
    }
    if (updateData.getEmail() != null) {
      logger.info("Updating email to: {}", updateData.getEmail());
      user.setEmail(updateData.getEmail());
    }
    if (updateData.getPassword() != null && !updateData.getPassword().isEmpty()) {
      logger.info("Updating password for user id: {}", userId);
      user.setPasswordHash(passwordEncoder.encode(updateData.getPassword()));
    }

    User updatedUser = userRepository.save(user);
    logger.info("User updated successfully: {}", updatedUser);
    return updatedUser;
  }

  /**
   * Retrieves a user by ID.
   *
   * @param userId the ID of the user
   * @return the found user
   * @throws RuntimeException if user not found
   */
  public User getUserById(int userId) {
    User user = userRepository.findByUserId(userId);
    if (user == null) {
      logger.error("User not found for id: {}", userId);
      throw new RuntimeException("User not found");
    }
    return user;
  }

  /**
   * Retrieves a user by username.
   *
   * @param username the username
   * @return the found user, or null if not found
   */
  public User getUserByUsername(String username) {
    logger.info("Fetching user by username: {}", username);
    return userRepository.findByUsername(username);
  }

  /**
   * Deletes a user by ID if they exist.
   *
   * @param userId the ID of the user to delete
   * @return true if deleted; false if not found
   */
  public boolean deleteUser(int userId) {
    if (userRepository.existsById(userId)) {
      logger.info("Deleting user with id: {}", userId);
      userRepository.deleteById(userId);
      return true;
    }
    logger.warn("Attempted to delete non-existent user with id: {}", userId);
    return false;
  }

  /**
   * Assigns a new role to an existing user.
   *
   * @param userId  the ID of the user
   * @param roleStr the new role name
   * @return true if assignment successful
   * @throws RuntimeException if user not found or role invalid
   */
  public boolean assignRole(int userId, String roleStr) {
    logger.info("Assigning role {} to user with id: {}", roleStr, userId);

    User user = userRepository.findByUserId(userId);
    if (user == null) {
      logger.error("User not found for id: {}", userId);
      throw new RuntimeException("User not found");
    }

    try {
      user.setRole(Role.valueOf(roleStr.toUpperCase()));
      userRepository.save(user);
      logger.info("Role assigned successfully to user id: {}", userId);
      return true;
    } catch (IllegalArgumentException e) {
      logger.error("Invalid role specified: {}", roleStr);
      throw new RuntimeException("Invalid role specified");
    }
  }

  /**
   * Retrieves all users.
   *
   * @return list of users
   */
  public List<User> getAllUsers() {
    logger.info("Fetching all users");
    return userRepository.findAll();
  }
}
