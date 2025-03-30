package com.tennisclub.controller;

import com.tennisclub.model.FinancialTransaction;
import com.tennisclub.service.FinancialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/financial")
public class FinancialController {

  @Autowired
  private FinancialService financialService;

  @PostMapping("/transaction")
  public ResponseEntity<?> processTransaction(@RequestBody FinancialTransaction transaction) {
    try {
      FinancialTransaction processedTransaction = financialService.processTransaction(transaction);
      return ResponseEntity.ok(processedTransaction);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Transaction processing failed: " + e.getMessage());
    }
  }

  @PostMapping("/refund")
  public ResponseEntity<?> processRefund(@RequestBody FinancialTransaction transaction) {
    try {
      FinancialTransaction refundedTransaction = financialService.processRefund(transaction);
      return ResponseEntity.ok(refundedTransaction);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Refund processing failed: " + e.getMessage());
    }
  }

  @GetMapping("/report")
  public ResponseEntity<?> generateReport() {
    try {
      String report = financialService.generateReport();
      return ResponseEntity.ok(report);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Report generation failed: " + e.getMessage());
    }
  }

  @PostMapping("/reconcile")
  public ResponseEntity<?> reconcileAccounts() {
    try {
      boolean result = financialService.reconcileAccounts();
      if (result) {
        return ResponseEntity.ok("Reconciliation successful.");
      } else {
        return ResponseEntity.badRequest().body("Reconciliation failed.");
      }
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Error: " + e.getMessage());
    }
  }
}
