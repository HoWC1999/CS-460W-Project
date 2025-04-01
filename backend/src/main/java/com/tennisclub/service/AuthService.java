package com.tennisclub.service;

import com.tennisclub.model.JWTToken;
import com.tennisclub.model.User;
import com.tennisclub.repository.UserRepository;
import com.tennisclub.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthService {

  @Autowired
  private UserRepository userRepository;

  // Temporarily disable password encoding for testing
  // @Autowired
  // private PasswordEncoder passwordEncoder;

  @Autowired
  private JwtUtil jwtUtil;

  public JWTToken login(String username, String password) {
    User user = userRepository.findByUsername(username);
    if(user == null) {
      throw new RuntimeException("User not found");
    }
    // Directly compare the raw password with the stored value (which, for now, should be plain text)
    if(!password.equals(user.getPasswordHash())) {
      throw new RuntimeException("Invalid password");
    }
    // Generate JWT token with 1 hour expiry
    String token = jwtUtil.generateToken(user.getUsername());
    return new JWTToken(token, new Date(System.currentTimeMillis() + 3600000));
  }

  public void logout(String token) {
    // In a stateless JWT system, logout may simply be handled on the client side.
    // Optionally, implement token blacklisting.
  }
}
