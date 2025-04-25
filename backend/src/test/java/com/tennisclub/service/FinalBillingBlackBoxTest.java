package com.tennisclub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tennisclub.model.FinancialTransaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BillingBlackBoxTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testSuccessfulPayment() throws Exception {
        FinancialTransaction transaction = new FinancialTransaction();
        transaction.setUserId(1);
        transaction.setAmount(new BigDecimal("50.00"));
        transaction.setType("credit_card");

        mockMvc.perform(post("/api/financial/transaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(transaction)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    public void testPaymentErrorHandling() throws Exception {
        FinancialTransaction transaction = new FinancialTransaction();
        transaction.setUserId(null); // invalid user
        transaction.setAmount(new BigDecimal("-10.00")); // invalid amount
        transaction.setType("unknown");

        mockMvc.perform(post("/api/financial/transaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(transaction)))
                .andExpect(status().isBadRequest());
    }
}
