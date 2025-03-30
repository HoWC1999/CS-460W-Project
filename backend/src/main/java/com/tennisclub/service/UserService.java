package com.tennisclub.service;

import com.tennisclub.model.User;
import com.tennisclub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public User register(User user) {
    // Here you might hash the password and validate the data
    return userRepository.save(user);
  }

  public User updateProfile(int userId, User newData) {
    Optional<User> optionalUser = userRepository.findById(userId);
    if (optionalUser.isPresent()){
      User user = optionalUser.get();
      user.setEmail(newData.getEmail());
      user.setUsername(newData.getUsername());
      // Update additional fields as needed
      return userRepository.save(user);
    }
    throw new RuntimeException("User not found");
  }

  public User getUserById(int id) {
    return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
  }

  public boolean deleteUser(int id) {
    if(userRepository.existsById(id)){
      userRepository.deleteById(id);
      return true;
    }
    return false;
  }

  public boolean assignRole(int id, String role) {
    Optional<User> optionalUser = userRepository.findById(id);
    if(optionalUser.isPresent()){
      User user = optionalUser.get();
      user.setRole(com.tennisclub.model.enums.Role.valueOf(role.toUpperCase()));
      userRepository.save(user);
      return true;
    }
    return false;
  }
}
