package com.tennisclub.controller;

import com.tennisclub.dto.LoginRequest;
import com.tennisclub.model.JWTToken;
import com.tennisclub.repository.AuditLogRepository;
import com.tennisclub.service.AuditLogService;
import com.tennisclub.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  private AuthService authService;

  @Autowired
  private AuditLogRepository auditLogRepository;

  @Autowired
  private AuditLogService auditLogService;

  private PasswordEncoder encoder;
  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
    try {
      // Use getPassword() to retrieve the raw password

      JWTToken token = authService.login(loginRequest.getUsername(), loginRequest.getPassword());

      auditLogService.logEvent(
        "SYSTEM",
        "DELETE: ",
        "User" + loginRequest.getUsername() + " login"
      );
      return ResponseEntity.ok(token);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Login failed: " + e.getMessage());
    }
  }

  @PostMapping("/logout")
  public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
    try {
      auditLogService.logEvent(
        "SYSTEM",
        "LOG-OUT: ",
        "Log out"
      );
      authService.logout(token);
      return ResponseEntity.ok("Logout successful");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Logout failed: " + e.getMessage());
    }
  }
}
