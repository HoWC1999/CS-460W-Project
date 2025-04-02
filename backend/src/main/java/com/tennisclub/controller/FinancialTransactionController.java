package com.tennisclub.controller;

import com.tennisclub.model.FinancialTransaction;
import com.tennisclub.repository.FinancialTransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/financial")
public class FinancialTransactionController {

  private static final Logger logger = LoggerFactory.getLogger(FinancialTransactionController.class);

  @Autowired
  private FinancialTransactionRepository transactionRepository;

  @GetMapping("/transactions")
  public ResponseEntity<?> getAllTransactions() {
    try {
      List<FinancialTransaction> transactions = transactionRepository.findAll();
      logger.info("Fetched {} transactions", transactions.size());
      return ResponseEntity.ok(transactions);
    } catch (Exception e) {
      logger.error("Error fetching transactions: {}", e.getMessage(), e);
      return ResponseEntity.status(500).body("Error fetching transactions: " + e.getMessage());
    }
  }
}
