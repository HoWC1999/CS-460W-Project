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
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  private UserService userService;

  private User existingUser;

  @BeforeEach
  void setUp() {
    existingUser = new User();
    existingUser.setUserId(2);
    existingUser.setUsername("oldUser");
    existingUser.setEmail("old@example.com");
    existingUser.setPasswordHash("oldPass");
    existingUser.setRole(Role.MEMBER);
    existingUser.setStatus("Active");
  }

  @Test
  void updateProfile_userNotFound_shouldThrowException() {
    // Arrange: Stub findById(2) to simulate that the user is not found.
    when(userRepository.findById(eq(2))).thenReturn(null);

    UpdateUserDTO updateUserDTO = new UpdateUserDTO();
    updateUserDTO.setUsername("newusername");

    // Act and Assert: Expect an exception with the message "User not found"
    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      userService.updateProfile(2, updateUserDTO);
    });
    assertEquals("User not found", exception.getMessage());
  }

  @Test
  void updateProfile_success_shouldUpdateFields() {
    // Arrange:
    int userId = 2;
    UpdateUserDTO updateData = new UpdateUserDTO();
    updateData.setUsername("newUsername");
    updateData.setEmail("new@mail.com");
    updateData.setPassword("newPassword");

    User existingUser = new User();
    existingUser.setUserId(userId);
    existingUser.setUsername("oldUsername");
    existingUser.setEmail("old@mail.com");
    // ... initialize other properties as needed

    when(userRepository.findById(eq(userId))).thenReturn(existingUser);
    when(passwordEncoder.encode(anyString())).thenReturn("hashedNewPassword");
    when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

    // Act:
    User updatedUser = userService.updateProfile(userId, updateData);

    // Assert:
    assertNotNull(updatedUser, "The updated user should not be null.");
    assertEquals("newUsername", updatedUser.getUsername());
    assertEquals("new@mail.com", updatedUser.getEmail());
    assertEquals("hashedNewPassword", updatedUser.getPasswordHash());
  }
}
