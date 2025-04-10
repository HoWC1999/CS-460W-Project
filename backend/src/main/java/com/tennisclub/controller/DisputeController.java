package com.tennisclub.controller;

import com.tennisclub.model.FinancialTransaction;
import com.tennisclub.service.FinancialService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/disputes")
public class DisputeController {

  private static final Logger logger = LoggerFactory.getLogger(DisputeController.class);

  @Autowired
  private FinancialService financialService;

  @PostMapping("/create/{transactionId}")
  public ResponseEntity<?> createDispute(@PathVariable int transactionId, @RequestBody Map<String, String> disputeData) {
    try {
      String reason = disputeData.get("reason");
      if (reason == null || reason.isEmpty()) {
        return ResponseEntity.badRequest().body("Dispute reason is required");
      }
      FinancialTransaction transaction = financialService.markAsDisputed(transactionId, reason);
      logger.info("Dispute created for transaction {}: {}", transactionId, reason);
      return ResponseEntity.ok(transaction);
    } catch (Exception e) {
      logger.error("Dispute creation failed for transaction {}: {}", transactionId, e.getMessage(), e);
      return ResponseEntity.badRequest().body("Dispute creation failed: " + e.getMessage());
    }
  }
}
