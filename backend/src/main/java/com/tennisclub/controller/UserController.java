package com.tennisclub.controller;

import com.tennisclub.model.User;
import com.tennisclub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication; // Make sure Spring Security is available
import org.springframework.web.bind.annotation.*;
import com.tennisclub.util.JwtUtil;

@RestController
@RequestMapping("/api/users")
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private JwtUtil JwtUtil;

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody User user) {
    try {
      User registeredUser = userService.register(user);
      return ResponseEntity.ok(registeredUser);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Registration failed: " + e.getMessage());
    }
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<?> updateProfile(@PathVariable int id, @RequestBody User user) {
    try {
      User updatedUser = userService.updateProfile(id, user);
      return ResponseEntity.ok(updatedUser);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Update failed: " + e.getMessage());
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getUser(@PathVariable int id) {
    try {
      User user = userService.getUserById(id);
      return ResponseEntity.ok(user);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("User not found: " + e.getMessage());
    }
  }

  // New endpoint to get the currently authenticated user's details
  @GetMapping("/me")
  public ResponseEntity<?> getCurrentUser(Authentication authentication,
                                          @RequestHeader(value = "Authorization", required = false) String authHeader) {
    String username = null;
    if (authentication != null) {
      username = authentication.getName();
    } else if (authHeader != null && authHeader.startsWith("Bearer ")) {
      String token = authHeader.substring(7);
      try {
        username = com.tennisclub.util.JwtUtil.validateToken(token);
      } catch (Exception e) {
        return ResponseEntity.status(401).body("Invalid token: " + e.getMessage());
      }
    } else {
      return ResponseEntity.status(401).body("No authentication provided");
    }

    User user = userService.getUserByUsername(username);
    if (user == null) {
      return ResponseEntity.status(404).body("User not found");
    }
    return ResponseEntity.ok(user);
  }
}
