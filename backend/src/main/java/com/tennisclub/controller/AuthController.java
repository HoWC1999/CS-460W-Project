package com.tennisclub.controller;

import com.tennisclub.model.JWTToken;
import com.tennisclub.model.User;
import com.tennisclub.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  private AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody User loginRequest) {
    try {
      JWTToken token = authService.login(loginRequest.getUsername(), loginRequest.getPasswordHash());
      return ResponseEntity.ok(token);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Login failed: " + e.getMessage());
    }
  }

  @PostMapping("/logout")
  public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
    try {
      authService.logout(token);
      return ResponseEntity.ok("Logout successful");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Logout failed: " + e.getMessage());
    }
  }
}

