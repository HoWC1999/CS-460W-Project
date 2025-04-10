package com.tennisclub.service;

import com.tennisclub.dto.UpdateUserDTO;
import com.tennisclub.model.User;
import com.tennisclub.model.enums.Role;
import com.tennisclub.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

  private static final Logger logger = LoggerFactory.getLogger(UserService.class);

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public User register(User user) {
    logger.info("Registering user: {}", user.getUsername());
    if (userRepository.findByUsername(user.getUsername()) != null) {
      logger.error("Registration failed: Username {} already exists", user.getUsername());
      throw new RuntimeException("Username already exists");
    }
    // Assume user.getPassword() returns the raw password from the frontend.
    String rawPassword = user.getPassword();
    // Use your PasswordEncoder bean to encode the password.
    user.setPasswordHash(passwordEncoder.encode(rawPassword));
    user.setPassword(null);
    if (user.getRole() == null) {
      user.setRole(Role.MEMBER);
    }
    if (user.getStatus() == null) {
      user.setStatus("Active");
    }
    User savedUser = userRepository.save(user);
    logger.info("User registered successfully with id: {}", savedUser.getUserId());
    return savedUser;
  }

  public User updateProfile(int userId, UpdateUserDTO updateData) {
    logger.info("Attempting to update user with id: {}", userId);
    User user = userRepository.findById(userId);
    if (user == null) {
      logger.error("Update failed: No user found with id: {}", userId);
      throw new RuntimeException("User not found");
    }
    logger.debug("Existing user details: {}", user);
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
      String rawPassword = updateData.getPassword();
      user.setPasswordHash(passwordEncoder.encode(rawPassword));
    }
    User updatedUser = userRepository.save(user);
    logger.info("User updated successfully: {}", updatedUser);
    return updatedUser;
  }

  public User getUserById(int userId) {
    User user = userRepository.findById(userId);
    if (user == null) {
      logger.error("User not found for id: {}", userId);
      throw new RuntimeException("User not found");
    }
    return user;
  }

  public User getUserByUsername(String username) {
    logger.info("Fetching user by username: {}", username);
    return userRepository.findByUsername(username);
  }

  public boolean deleteUser(int userId) {
    if (userRepository.existsById(userId)) {
      logger.info("Deleting user with id: {}", userId);
      userRepository.deleteById(userId);
      return true;
    }
    logger.warn("Attempted to delete non-existent user with id: {}", userId);
    return false;
  }

  public boolean assignRole(int userId, String roleStr) {
    logger.info("Assigning role {} to user with id: {}", roleStr, userId);
    User user = userRepository.findById(userId);
    if (user == null) {
      logger.error("User not found for id: {}", userId);
      throw new RuntimeException("User not found");
    }
    try {
      user.setRole(Enum.valueOf(Role.class, roleStr.toUpperCase()));
      userRepository.save(user);
      logger.info("Role assigned successfully to user id: {}", userId);
      return true;
    } catch (IllegalArgumentException e) {
      logger.error("Invalid role specified: {}", roleStr);
      throw new RuntimeException("Invalid role specified");
    }
  }

  public List<User> getAllUsers() {
    logger.info("Fetching all users");
    return userRepository.findAll();
  }

}
