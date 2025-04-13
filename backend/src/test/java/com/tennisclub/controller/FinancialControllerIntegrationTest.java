package com.tennisclub.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tennisclub.dto.CourtReservationDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class FinancialTransactionControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  // DTO class for login; could be reused from AuthControllerIntegrationTest.
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
  void testChargeAnnualMembershipFeeEndpoint() throws Exception {
    // Perform login first to obtain a valid JWT token.
    LoginRequest loginRequest = new LoginRequest("admin", "pass");
    String loginJson = objectMapper.writeValueAsString(loginRequest);

    MvcResult loginResult = mockMvc.perform(post("/api/auth/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(loginJson))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.token").exists())
      .andReturn();

    String loginResponse = loginResult.getResponse().getContentAsString();
    JsonNode loginNode = objectMapper.readTree(loginResponse);
    String token = loginNode.get("token").asText();

    // Construct URL for charging the annual membership fee.
    // Adjust the parameter names as defined in your controller.
    String url = "/api/financial/membership?user=" + 1 + "&amount=400.00"; // Assume user with ID 1 exists.

    MvcResult result = mockMvc.perform(post(url)
        .header("Authorization", "Bearer " + token)
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.transactionId").exists())
      .andExpect(jsonPath("$.amount").value(400.00))
      .andReturn();

    String responseContent = result.getResponse().getContentAsString();
    JsonNode responseNode = objectMapper.readTree(responseContent);
    assertThat(responseNode.get("transactionId").asInt()).isGreaterThan(0);
  }
}
