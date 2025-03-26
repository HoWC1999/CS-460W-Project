package com.tennisclub.controller;

import com.tennisclub.model.JWTToken;
import com.tennisclub.model.User;
import com.tennisclub.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    @PostMapping("/login")
    public JWTToken login(@RequestBody User loginRequest) {
        return authService.login(loginRequest.getUsername(), loginRequest.getPasswordHash());
    }
    
    @PostMapping("/logout")
    public void logout(@RequestHeader("Authorization") String token) {
        authService.logout(token);
    }
}
