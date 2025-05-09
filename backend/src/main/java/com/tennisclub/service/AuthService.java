package com.tennisclub.service;

import com.tennisclub.model.JWTToken;
import com.tennisclub.model.User;
import com.tennisclub.repository.UserRepository;
import com.tennisclub.util.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private PasswordEncoder passwordEncoder;



  public JWTToken login(String username, String password) {
    User user = userRepository.findByUsername(username);
    if(user == null) {
      throw new RuntimeException("User not found");
    }
    // Directly compare the raw password with the stored value (which, for now, should be plain text)
    // Verify the raw password against the stored hashed password.
    if (!passwordEncoder.matches(password, user.getPasswordHash())) {
      throw new RuntimeException("Invalid password");
    }
    // Generate JWT token with 1 hour expiry
    String token = jwtUtil.generateToken(user.getUsername(), String.valueOf(user.getRole()));
    return new JWTToken(token, new Date(System.currentTimeMillis() + 3600000));
  }

  public void logout(String token) {

  }
}
