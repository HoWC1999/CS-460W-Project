package com.tennisclub.service;

import com.tennisclub.model.User;
import com.tennisclub.model.enums.Role;
import com.tennisclub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public User register(User user) {
    if (userRepository.findByUsername(user.getUsername()) != null) {
      throw new RuntimeException("Username already exists");
    }
    user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
    if (user.getRole() == null) {
      user.setRole(Role.MEMBER);
    }
    if (user.getStatus() == null) {
      user.setStatus("Active");
    }
    return userRepository.save(user);
  }

  public User updateProfile(int userId, User newData) {
    User user = userRepository.findById(userId);
    if (user == null) {
      throw new RuntimeException("User not found");
    }
    if (newData.getUsername() != null) {
      user.setUsername(newData.getUsername());
    }
    if (newData.getEmail() != null) {
      user.setEmail(newData.getEmail());
    }
    if (newData.getPasswordHash() != null && !newData.getPasswordHash().isEmpty()) {
      user.setPasswordHash(passwordEncoder.encode(newData.getPasswordHash()));
    }
    return userRepository.save(user);
  }

  public User getUserById(int userId) {
    return userRepository.findById(userId);
  }

  public User getUserByUsername(String username) {
    return userRepository.findByUsername(username);
  }


  public boolean deleteUser(int userId) {
    if(userRepository.existsById(userId)) {
      userRepository.deleteById(userId);
      return true;
    }
    return false;
  }

  public boolean assignRole(int userId, String roleStr) {
    Optional<User> optionalUser = Optional.ofNullable(userRepository.findById(userId));
    if(!optionalUser.isPresent()) {
      throw new RuntimeException("User not found");
    }
    User user = optionalUser.get();
    try {
      Role role = Role.valueOf(roleStr.toUpperCase());
      user.setRole(role);
      userRepository.save(user);
      return true;
    } catch (IllegalArgumentException e) {
      throw new RuntimeException("Invalid role specified");
    }
  }
}
