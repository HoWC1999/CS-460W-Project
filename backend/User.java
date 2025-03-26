package com.tennisclub.model;

import com.tennisclub.model.enums.Role;
import java.util.Map;

public class User {
    private int userId;
    private String username;
    private String passwordHash;
    private String email;
    private Role role;
    private String status; // e.g., "Active", "Terminated"
    
    // Getters and setters omitted for brevity

    public JWTToken login(String username, String password) {
        // Implement login logic
        return new JWTToken();
    }
    
    public void logout() {
        // Implement logout logic
    }
    
    public boolean updateProfile(Map<String, String> newData) {
        // Update profile logic
        return true;
    }

    public Role getRole() {
        return role;
    }
}
