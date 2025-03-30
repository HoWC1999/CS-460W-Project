package com.tennisclub.controller;

import com.tennisclub.model.User;
import com.tennisclub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

  @Autowired
  private UserService userService;

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
}
