package com.tennisclub.controller;

import com.tennisclub.model.User;
import com.tennisclub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.register(user);
    }
    
    @PutMapping("/update/{id}")
    public User updateProfile(@PathVariable int id, @RequestBody User user) {
        return userService.updateProfile(id, user);
    }
    
    @GetMapping("/{id}")
    public User getUser(@PathVariable int id) {
        return userService.getUserById(id);
    }
}
