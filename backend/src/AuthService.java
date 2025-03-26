package com.tennisclub.service;

import com.tennisclub.model.JWTToken;
import com.tennisclub.model.User;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    public JWTToken login(String username, String password) {
        // Validate credentials, generate JWT token, etc.
        JWTToken token = new JWTToken();
        // Stub code – implement token generation logic
        token.setToken("dummy-jwt-token");
        token.setExpiry(new java.util.Date(System.currentTimeMillis() + 3600000)); // 1 hour expiry
        return token;
    }
    
    public void logout(String token) {
        // Invalidate the token, clear session, etc.
    }
}
