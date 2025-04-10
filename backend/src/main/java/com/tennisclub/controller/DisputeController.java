package com.tennisclub.controller;

import com.tennisclub.model.FinancialTransaction;
import com.tennisclub.service.FinancialService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
      if (reason == null || reason.trim().isEmpty()) {
        String msg = "Dispute reason is required.";
        logger.warn(msg);
        return ResponseEntity.badRequest().body(msg);
      }
      logger.info("Creating dispute for transaction {} with reason: {}", transactionId, reason);
      FinancialTransaction transaction = financialService.markAsDisputed(transactionId, reason);
      logger.info("Dispute created successfully for transaction {}", transactionId);
      return ResponseEntity.ok(transaction);
    } catch (Exception e) {
      logger.error("Dispute creation failed for transaction {}: {}", transactionId, e.getMessage(), e);
      return ResponseEntity.badRequest().body("Dispute creation failed: " + e.getMessage());
    }
  }

  @GetMapping("/disputed")
  public ResponseEntity<?> getDisputedTransactions() {
    try {
      List<FinancialTransaction> disputedTransactions = financialService.getDisputedTransactions();
      return ResponseEntity.ok(disputedTransactions);
    } catch (Exception e) {
      logger.error("Error fetching disputed transactions: {}", e.getMessage(), e);
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body("Error fetching disputed transactions: " + e.getMessage());
    }
  }

  @PostMapping("/refund/{transactionId}")
  public ResponseEntity<?> processRefund(@PathVariable int transactionId) {
    try {
      logger.info("Processing refund for transaction: {}", transactionId);
      FinancialTransaction transaction = financialService.markAsRefundIssued(transactionId);
      logger.info("Refund processed for transaction: {}", transactionId);
      return ResponseEntity.ok(transaction);
    } catch (Exception e) {
      logger.error("Error processing refund for transaction {}: {}", transactionId, e.getMessage(), e);
      return ResponseEntity.badRequest().body("Error processing refund: " + e.getMessage());
    }
  }
}
