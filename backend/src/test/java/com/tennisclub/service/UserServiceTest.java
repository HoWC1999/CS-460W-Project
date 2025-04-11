// src/test/java/com/tennisclub/service/UserServiceTest.java
package com.tennisclub.service;

import com.tennisclub.dto.UpdateUserDTO;
import com.tennisclub.model.User;
import com.tennisclub.model.enums.Role;
import com.tennisclub.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserService userService;

  private User existingUser;

  @BeforeEach
  void setUp() {
    existingUser = new User();
    existingUser.setUserId(1);
    existingUser.setUsername("oldUser");
    existingUser.setEmail("old@example.com");
    existingUser.setPasswordHash("oldPass");
    existingUser.setRole(Role.MEMBER);
    existingUser.setStatus("Active");
  }

  @Test
  void updateProfile_userNotFound_shouldThrowException() {
    when(userRepository.findById(1)).thenReturn(null);
    UpdateUserDTO dto = new UpdateUserDTO();
    dto.setUsername("newUser");
    RuntimeException ex = assertThrows(RuntimeException.class, () -> userService.updateProfile(1, dto));
    assertEquals("User not found", ex.getMessage());
  }

  @Test
  void updateProfile_success_shouldUpdateFields() {
    UpdateUserDTO dto = new UpdateUserDTO();
    dto.setUsername("newUser");
    dto.setEmail("new@example.com");
    dto.setPassword("newPass");

    when(userRepository.findById(1)).thenReturn(existingUser);
    when(userRepository.save(existingUser)).thenReturn(existingUser);

    User updatedUser = userService.updateProfile(1, dto);

    assertEquals("newUser", updatedUser.getUsername());
    assertEquals("new@example.com", updatedUser.getEmail());
    // In production, the password should be encoded; here we assume the test bypasses encoding.
    assertNotEquals("newPass", updatedUser.getPasswordHash());
  }
}
