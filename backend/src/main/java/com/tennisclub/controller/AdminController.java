package com.tennisclub.controller;

import com.tennisclub.model.User;
import com.tennisclub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

  @Autowired
  private UserService userService;

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
  public ResponseEntity<?> assignRole(@PathVariable int id, @RequestParam String role) {
    try {
      boolean result = userService.assignRole(id, role);
      if (result) {
        return ResponseEntity.ok("Role assigned successfully.");
      } else {
        return ResponseEntity.badRequest().body("Role assignment failed.");
      }
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Error: " + e.getMessage());
    }
  }
}
