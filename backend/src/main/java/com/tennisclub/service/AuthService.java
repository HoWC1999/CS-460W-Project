package com.tennisclub.service;

import com.tennisclub.model.JWTToken;
import com.tennisclub.model.User;
import com.tennisclub.repository.UserRepository;
import com.tennisclub.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JwtUtil jwtUtil;

  public JWTToken login(String username, String password) {
    User user = userRepository.findByUsername(username);
    if(user == null) {
      throw new RuntimeException("User not found");
    }
    // Compare raw password with stored (hashed) password
    if(!passwordEncoder.matches(password, user.getPasswordHash())) {
      throw new RuntimeException("Invalid password");
    }
    // Generate JWT token with 1 hour expiry
    String token = jwtUtil.generateToken(user.getUsername());
    JWTToken jwtToken = new JWTToken(token, new java.util.Date(System.currentTimeMillis() + 3600000));
    return jwtToken;
  }

  public void logout(String token) {
    // In a stateless JWT system, logout may simply be handled on the client side.
    // Optionally, you might implement token blacklisting.
  }
}
