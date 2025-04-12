package com.tennisclub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tennisclub.model.JWTToken;
import com.tennisclub.model.User;
import com.tennisclub.model.enums.Role;
import com.tennisclub.repository.UserRepository;
import com.tennisclub.service.AuthService;
import com.tennisclub.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  // In an integration test, the full context is loaded so you can actually hit the controller.
  @Test
  public void testLoginEndpointSuccess() throws Exception {
    // Prepare login credentials (ensure that a user with these credentials exists in your test DB)
    String username = "admin";
    String password = "pass";

    // Construct the JSON login request.
    String loginRequest = objectMapper.writeValueAsString(
      new LoginRequest(username, password)
    );

    mockMvc.perform(post("/api/auth/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(loginRequest))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.token").exists());

    MvcResult result = mockMvc.perform(post("/api/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"username\": \"admin\", \"password\": \"pass\"}"))
      .andReturn();
    System.out.println("Response: " + result.getResponse().getContentAsString());
  }

  // Additional tests for invalid credentials can be added here.

  // Define a simple DTO class for testing login request.
  public static class LoginRequest {
    private String username;
    private String password;
    // Constructors, getters and setters
    public LoginRequest() {}
    public LoginRequest(String username, String password) {
      this.username = username;
      this.password = password;
    }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
  }
}
