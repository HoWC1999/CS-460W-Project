package com.tennisclub.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tennisclub.dto.CourtReservationDTO;
import com.tennisclub.controller.AuthControllerIntegrationTest.LoginRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ReservationControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  // DTO for login requests for testing purposes.
  public static class LoginRequest {
    private String username;
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String username, String password) {
      this.username = username;
      this.password = password;
    }

    // Getters and setters...
    public String getUsername() {
      return username;
    }
    public void setUsername(String username) {
      this.username = username;
    }
    public String getPassword() {
      return password;
    }
    public void setPassword(String password) {
      this.password = password;
    }
  }

  @Test
  void testCreateReservation_WithRealLogin() throws Exception {
    // First, perform a login to get a valid JWT token.
    String loginRequestJson = objectMapper.writeValueAsString(
      new LoginRequest("admin", "pass")
    );

    MvcResult loginResult = mockMvc.perform(post("/api/auth/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(loginRequestJson))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.token").exists())
      .andReturn();

    // Extract the token from the login response.
    String loginResponse = loginResult.getResponse().getContentAsString();
    JsonNode loginJsonNode = objectMapper.readTree(loginResponse);
    String token = loginJsonNode.get("token").asText();

    // Create a reservation DTO with valid test data.
    CourtReservationDTO reservationDTO = new CourtReservationDTO( 2, "2025-05-01", "10:00");
    String reservationJson = objectMapper.writeValueAsString(reservationDTO);

    // Perform the secured reservation creation request using the JWT token.
    mockMvc.perform(post("/api/reservations/create")
        .header("Authorization", "Bearer " + token)
        .contentType(MediaType.APPLICATION_JSON)
        .content(reservationJson))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.reservationId").exists());
  }
}
