package com.tennisclub.controller;

import com.tennisclub.dto.RoleAssignmentDTO;
import com.tennisclub.model.GuestPass;
import com.tennisclub.model.User;
import com.tennisclub.repository.GuestPassRepository;

import com.tennisclub.service.AuditLogService;

import com.tennisclub.service.GuestPassService;
import com.tennisclub.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

  private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

  private final GuestPassService guestPassService;

  private final AuditLogService auditLogService;

  private final UserService userService;

  public AdminController(UserService userService, GuestPassService guestPassService, AuditLogService auditLogService, GuestPassRepository guestPassRepository) {
    this.userService = userService;
    this.guestPassService = guestPassService;
    this.auditLogService = auditLogService;
  }

  // GET all users
  @GetMapping("/users")
  public ResponseEntity<?> getAllUsers() {
    try {
      List<User> users = userService.getAllUsers();
      auditLogService.logEvent(
        "SYSTEM",
        "VIEW_USERS",
        "Fetched all users, count=" + users.size()
      );

      logger.info("Fetched {} users", users.size());
      return ResponseEntity.ok(users);
    } catch(Exception e) {
      logger.error("Error fetching users: {}", e.getMessage());
      return ResponseEntity.badRequest().body("Error fetching users: " + e.getMessage());
    }
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<?> deleteUser(@PathVariable int id) {
    try {
      boolean result = userService.deleteUser(id);
      if (result) {

        auditLogService.logEvent(
          "SYSTEM",
          "DELETE: ",
          "Deleted user id=" + id
        );
        return ResponseEntity.ok("User deleted successfully.");
      } else {
        return ResponseEntity.badRequest().body("User deletion failed.");
      }
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Error: " + e.getMessage());
    }
  }

  @PostMapping("/assignRole/{id}")
  public ResponseEntity<?> assignRole(@PathVariable int id, @RequestBody RoleAssignmentDTO roleAssignmentDTO) {
    try {
      boolean success = userService.assignRole(id, roleAssignmentDTO.getRole());
      if (success) {
        return ResponseEntity.ok("Role assigned successfully.");
      } else {
        return ResponseEntity.badRequest().body("Failed to assign role.");
      }
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Error assigning role: " + e.getMessage());
    }
  }
  @GetMapping("/getAll")
  public ResponseEntity<?> getAllPasses() {
    try {
      List<GuestPass> guestPass = guestPassService.getAllPasses();
      logger.info("Fetched {} guest passes", guestPass.size());
      return ResponseEntity.ok(guestPass);
    } catch (Exception e) {
      logger.error("Error fetching guest passes: {}", e.getMessage(),e);
      return ResponseEntity.status(500).body("Error fetching guest passes:" + e.getMessage());
    }
  }
}
