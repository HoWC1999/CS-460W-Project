package com.tennisclub.service;

import com.tennisclub.model.JWTToken;
import com.tennisclub.model.User;
import com.tennisclub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    
    public JWTToken login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPasswordHash().equals(password)) {
            JWTToken token = new JWTToken();
            token.setToken(UUID.randomUUID().toString());
            token.setExpiry(new Date(System.currentTimeMillis() + 3600000)); // 1-hour expiry
            return token;
        }
        throw new RuntimeException("Invalid credentials");
    }
    
    public void logout(String token) {
        // Invalidate the token if maintained in a token store (stub implementation)
    }
}
