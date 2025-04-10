package com.tennisclub.controller;

import com.tennisclub.model.FinancialTransaction;
import com.tennisclub.service.FinancialService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/financial")
public class FinancialTransactionController {

  private static final Logger logger = LoggerFactory.getLogger(FinancialTransactionController.class);

  @Autowired
  private FinancialService financialService;

  @PostMapping("/transaction")
  public ResponseEntity<?> processTransaction(@RequestBody FinancialTransaction transaction) {
    try {
      FinancialTransaction processed = financialService.processTransaction(transaction);
      return ResponseEntity.ok(processed);
    } catch (Exception e) {
      logger.error("Error processing transaction: {}", e.getMessage());
      return ResponseEntity.badRequest().body("Error processing transaction: " + e.getMessage());
    }
  }

  @PostMapping("/refund")
  public ResponseEntity<?> processRefund(@RequestBody FinancialTransaction transaction) {
    try {
      FinancialTransaction processed = financialService.processRefund(transaction);
      return ResponseEntity.ok(processed);
    } catch (Exception e) {
      logger.error("Error processing refund: {}", e.getMessage());
      return ResponseEntity.badRequest().body("Error processing refund: " + e.getMessage());
    }
  }

  @PostMapping("/membership")
  public ResponseEntity<?> chargeAnnualMembershipFee(@RequestParam int user, @RequestParam BigDecimal amount) {
    try {
      FinancialTransaction transaction = financialService.chargeAnnualMembershipFee(user, amount);
      logger.info("Charged annual membership fee for user {}: {}", user, transaction);
      return ResponseEntity.ok(transaction);
    } catch (Exception e) {
      logger.error("Error charging membership fee: {}", e.getMessage());
      return ResponseEntity.badRequest().body("Error charging membership fee: " + e.getMessage());
    }
  }

  @PostMapping("/late")
  public ResponseEntity<?> applyLateFee(@RequestParam int userId, @RequestParam BigDecimal baseAmount) {
    try {
      FinancialTransaction transaction = financialService.applyLateFee(userId, baseAmount);
      logger.info("Applied late fee for user {}: {}", userId, transaction);
      return ResponseEntity.ok(transaction);
    } catch (Exception e) {
      logger.error("Error applying late fee: {}", e.getMessage());
      return ResponseEntity.badRequest().body("Error applying late fee: " + e.getMessage());
    }
  }

  @GetMapping("/history")
  public ResponseEntity<?> getBillingHistory(@RequestParam int userId) {
    try {
      List<FinancialTransaction> history = financialService.getBillingHistory(userId);
      return ResponseEntity.ok(history);
    } catch (Exception e) {
      logger.error("Error fetching billing history: {}", e.getMessage());
      return ResponseEntity.badRequest().body("Error fetching billing history: " + e.getMessage());
    }
  }

  @GetMapping("/all")
  public ResponseEntity<?> getAllBilling() {
    try {
      List<FinancialTransaction> transactions = financialService.getAllBilling();
      logger.info("Fetched {} billing records", transactions.size());
      return ResponseEntity.ok(transactions);
    } catch (Exception e) {
      logger.error("Error fetching billing records: {}", e.getMessage());
      return ResponseEntity.badRequest().body("Error fetching billing records: " + e.getMessage());
    }
  }
  @PostMapping("/pay/{id}")
  public ResponseEntity<?> payBill(@PathVariable int id) {
    try {
      FinancialTransaction updatedTransaction = financialService.payBill(id);
      return ResponseEntity.ok(updatedTransaction);
    } catch (Exception e) {
      logger.error("Error paying bill: {}", e.getMessage());
      return ResponseEntity.badRequest().body("Error paying bill: " + e.getMessage());
    }
  }
  @PostMapping("/cardPayment/{billingId}")
  public ResponseEntity<?> processCardPayment(@PathVariable int billingId, @RequestParam String paymentMethodId) {
    try {
      FinancialTransaction transaction = financialService.processCardPayment(billingId, paymentMethodId);
      return ResponseEntity.ok(transaction);
    } catch (Exception e) {
      logger.error("Error processing card payment: {}", e.getMessage());
      return ResponseEntity.badRequest().body("Error processing card payment: " + e.getMessage());
    }
  }
}
