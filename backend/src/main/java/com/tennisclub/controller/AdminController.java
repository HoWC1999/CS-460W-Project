package com.tennisclub.controller;

import com.tennisclub.dto.RoleAssignmentDTO;
import com.tennisclub.model.User;
import com.tennisclub.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

  private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

  @Autowired
  private UserService userService;

  // GET all users
  @GetMapping("/users")
  public ResponseEntity<?> getAllUsers() {
    try {
      List<User> users = userService.getAllUsers();
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
}
