// src/test/java/com/tennisclub/service/AuthServiceTest.java
package com.tennisclub.service;

import com.tennisclub.model.JWTToken;
import com.tennisclub.model.User;
import com.tennisclub.model.enums.Role;
import com.tennisclub.repository.UserRepository;
import com.tennisclub.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Date;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private JwtUtil jwtUtil;

  @InjectMocks
  private AuthService authService;

  private User testUser;

  @BeforeEach
  void setUp() {
    testUser = new User();
    testUser.setUserId(1);
    testUser.setUsername("testUser");
    // For this test the "raw password" is stored unencoded for simplicity.
    testUser.setPasswordHash("rawPass");
    testUser.setRole(Role.MEMBER);
    testUser.setEmail("test@example.com");
  }

  @Test
  void login_userNotFound_shouldThrowException() {
    when(userRepository.findByUsername("nonexistent")).thenReturn(null);
    RuntimeException ex = assertThrows(RuntimeException.class, () -> authService.login("nonexistent", "whatever"));
    assertEquals("User not found", ex.getMessage());
  }

  @Test
  void login_invalidPassword_shouldThrowException() {
    when(userRepository.findByUsername("testUser")).thenReturn(testUser);
    RuntimeException ex = assertThrows(RuntimeException.class, () -> authService.login("testUser", "wrongPass"));
    assertEquals("Invalid password", ex.getMessage());
  }

  @Test
  void login_validCredentials_shouldReturnJWTToken() {
    when(userRepository.findByUsername("testUser")).thenReturn(testUser);
    when(jwtUtil.generateToken("testUser", "MEMBER")).thenReturn("dummyToken");
    JWTToken token = authService.login("testUser", "rawPass");
    assertNotNull(token);
    assertEquals("dummyToken", token.getToken());
    // You can also add assertions about the expiry if needed.
    assertTrue(token.getExpiry().after(new Date()));
  }
}
