package com.tennisclub.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.tennisclub.model.JWTToken;
import com.tennisclub.model.User;
import com.tennisclub.repository.UserRepository;
import com.tennisclub.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Mock
  private JwtUtil jwtUtil;

  @InjectMocks
  private AuthService authService;

  private User testUser;

  @BeforeEach
  public void setUp() {
    // Initialize a test user. Note that user.getPassword() must return the stored (hashed) password.
    testUser = new User();
    testUser.setUsername("testuser");
    // For testing purposes, assume that "hashedpassword" is the hashed version of "pass".
    testUser.setPasswordHash("hashedpassword");
    testUser.setEmail("test@test.com");
    // You can also set additional fields as needed.
  }

  @Test
  public void login_invalidPassword_shouldThrowException() {
    // Arrange: Setup mocks so that the invalid password scenario occurs.
    when(userRepository.findByUsername("testuser")).thenReturn(testUser);
    // Simulate that the raw password "wrongpassword" does not match the stored hash.
    when(passwordEncoder.matches("wrongpassword", testUser.getPasswordHash())).thenReturn(false);

    // Act & Assert: Verify that a RuntimeException is thrown with "Invalid password".
    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      authService.login("testuser", "wrongpassword");
    });
    assertEquals("Invalid password", exception.getMessage());
  }

  @Test
  public void login_validCredentials_shouldReturnJWTToken() {
    // Arrange: Setup mocks for a successful login.
    when(userRepository.findByUsername("testuser")).thenReturn(testUser);
    when(passwordEncoder.matches("pass", testUser.getPasswordHash())).thenReturn(true);
    // Use eq() matcher for the raw string "testuser" so that all arguments use matchers
    when(jwtUtil.generateToken(eq("testuser"), anyString())).thenReturn("dummyToken");

    // Act: Call the login method.
    JWTToken token = authService.login("testuser", "pass");

    // Assert: Verify that a valid token is returned.
    assertNotNull(token);
    assertEquals("dummyToken", token.getToken());
  }
}
