package com.tennisclub.controller;

import com.tennisclub.model.BillingTransaction;
import com.tennisclub.service.BillingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/billing")
public class BillingController {

  private static final Logger logger = LoggerFactory.getLogger(BillingController.class);

  @Autowired
  private BillingService billingService;

  @PostMapping("/membership")
  public ResponseEntity<?> chargeAnnualMembershipFee(@RequestParam int userId, @RequestParam BigDecimal amount) {
    try {
      BillingTransaction billing = billingService.chargeAnnualMembershipFee(userId, amount);
      return ResponseEntity.ok(billing);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Error charging membership fee: " + e.getMessage());
    }
  }

  @PostMapping("/late")
  public ResponseEntity<?> applyLateFee(@RequestParam int userId, @RequestParam BigDecimal baseAmount) {
    try {
      BillingTransaction billing = billingService.applyLateFee(userId, baseAmount);
      return ResponseEntity.ok(billing);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Error applying late fee: " + e.getMessage());
    }
  }

  @GetMapping("/history")
  public ResponseEntity<?> getBillingHistory(@RequestParam int userId) {
    try {
      List<BillingTransaction> history = billingService.getBillingHistory(userId);
      return ResponseEntity.ok(history);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Error fetching billing history: " + e.getMessage());
    }
  }
  // Get all billing records (for treasurer/admin)
  @GetMapping("/all")
  public ResponseEntity<?> getAllBilling() {
    try {
      List<BillingTransaction> transactions = billingService.getAllBilling();
      logger.info("Fetched {} billing records", transactions.size());
      return ResponseEntity.ok(transactions);
    } catch (Exception e) {
      logger.error("Error fetching all billing records: {}", e.getMessage());
      return ResponseEntity.badRequest().body("Error fetching billing records: " + e.getMessage());
    }
  }
}

