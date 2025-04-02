package com.tennisclub.controller;

import com.tennisclub.dto.UpdateUserDTO;
import com.tennisclub.model.User;
import com.tennisclub.service.UserService;
import com.tennisclub.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  @Autowired
  private UserService userService;

  @Autowired
  private JwtUtil jwtUtil;

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody User user) {
    try {
      User registeredUser = userService.register(user);
      logger.info("User registered: {}", registeredUser);
      return ResponseEntity.ok(registeredUser);
    } catch (Exception e) {
      logger.error("Registration failed: {}", e.getMessage());
      return ResponseEntity.badRequest().body("Registration failed: " + e.getMessage());
    }
  }

  @PutMapping("/me")
  public ResponseEntity<?> updateMyProfile(@RequestBody UpdateUserDTO updateData, Authentication authentication) {
    try {
      if (authentication == null || authentication.getName() == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not authenticated");
      }
      String oldUsername = authentication.getName();
      logger.info("Old username from token: {}", oldUsername);

      User user = userService.getUserByUsername(oldUsername);
      if (user == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
      }
      logger.info("Updating profile for user id: {}", user.getUserId());
      User updatedUser = userService.updateProfile(user.getUserId(), updateData);
      logger.info("User updated successfully: {}", updatedUser);

      // Force re-login by not returning a new token.
      // Optionally, you can add logic to clear server-side session data if applicable.
      return ResponseEntity.ok("Profile updated successfully. Please log in again.");
    } catch (Exception e) {
      logger.error("Update profile failed: {}", e.getMessage(), e);
      return ResponseEntity.badRequest().body("Update failed: " + e.getMessage());
    }
  }


  @PutMapping("/update/{id}")
  public ResponseEntity<?> updateProfile(@PathVariable int id, @RequestBody UpdateUserDTO updateData) {
    try {
      logger.info("Updating profile for user id: {}", id);
      User updatedUser = userService.updateProfile(id, updateData);
      logger.info("Profile updated: {}", updatedUser);
      return ResponseEntity.ok(updatedUser);
    } catch (Exception e) {
      logger.error("Update failed for user id {}: {}", id, e.getMessage());
      return ResponseEntity.badRequest().body("Update failed: " + e.getMessage());
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getUser(@PathVariable int id) {
    try {
      User user = userService.getUserById(id);
      return ResponseEntity.ok(user);
    } catch (Exception e) {
      logger.error("Error fetching user with id {}: {}", id, e.getMessage());
      return ResponseEntity.badRequest().body("User not found: " + e.getMessage());
    }
  }

  @GetMapping("/me")
  public ResponseEntity<?> getCurrentUser(Authentication authentication,
                                          @RequestHeader(value = "Authorization", required = false) String authHeader) {
    String username = null;
    if (authentication != null) {
      username = authentication.getName();
      logger.info("Authentication provided. Username: {}", username);
    } else if (authHeader != null && authHeader.startsWith("Bearer ")) {
      String token = authHeader.substring(7);
      try {
        username = jwtUtil.validateToken(token);
        logger.info("Token validated. Username: {}", username);
      } catch (Exception e) {
        logger.error("Invalid token: {}", e.getMessage());
        return ResponseEntity.status(401).body("Invalid token: " + e.getMessage());
      }
    } else {
      logger.warn("No authentication provided");
      return ResponseEntity.status(401).body("No authentication provided");
    }

    User user = userService.getUserByUsername(username);
    if (user == null) {
      logger.error("User not found for username: {}", username);
      return ResponseEntity.status(404).body("User not found");
    }
    logger.info("Returning user: {}", user);
    return ResponseEntity.ok(user);
  }
}
