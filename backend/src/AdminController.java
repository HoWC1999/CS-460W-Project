package com.tennisclub.controller;

import com.tennisclub.model.User;
import com.tennisclub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserService userService;
    
    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        return userService.deleteUser(id) ? "User deleted" : "Deletion failed";
    }
    
    @PostMapping("/assignRole/{id}")
    public String assignRole(@PathVariable int id, @RequestParam String role) {
        return userService.assignRole(id, role) ? "Role assigned" : "Role assignment failed";
    }
}
