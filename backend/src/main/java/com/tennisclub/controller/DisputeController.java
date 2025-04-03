package com.tennisclub.controller;

import com.tennisclub.model.FinancialTransaction;
import com.tennisclub.service.BillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/disputes")
public class DisputeController {

  @Autowired
  private BillingService billingService;

  @PostMapping("/create/{billingId}")
  public ResponseEntity<?> createDispute(@PathVariable int billingId, @RequestBody Map<String, String> disputeData) {
    try {
      String reason = disputeData.get("reason");
      FinancialTransaction transaction = billingService.markAsDisputed(billingId, reason);
      return ResponseEntity.ok(transaction);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Dispute creation failed: " + e.getMessage());
    }
  }
}
