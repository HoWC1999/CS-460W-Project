package com.tennisclub.service;

import com.tennisclub.model.FinancialReport;
import com.tennisclub.model.FinancialTransaction;
import com.tennisclub.model.enums.TransactionStatus;
import com.tennisclub.repository.FinancialReportRepository;
import com.tennisclub.repository.FinancialTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class FinancialService {

  @Autowired
  private FinancialTransactionRepository transactionRepository;

  @Autowired
  private FinancialReportRepository reportRepository;

  // Process a payment or billing transaction
  public FinancialTransaction processTransaction(FinancialTransaction transaction) {
    if (transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
      throw new RuntimeException("Transaction amount must be greater than zero");
    }
    transaction.setStatus(TransactionStatus.PENDING);
    // Simulate processing...
    transaction.setStatus(TransactionStatus.SUCCESS);
    return transactionRepository.save(transaction);
  }

  // Process a refund transaction
  public FinancialTransaction processRefund(FinancialTransaction transaction) {
    if (transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
      throw new RuntimeException("Refund amount must be greater than zero");
    }
    transaction.setStatus(TransactionStatus.SUCCESS);
    return transactionRepository.save(transaction);
  }

  // Charge the annual membership fee
  public FinancialTransaction chargeAnnualMembershipFee(int userId, BigDecimal amount) {
    FinancialTransaction transaction = new FinancialTransaction();
    transaction.setUser(new com.tennisclub.model.User());
    transaction.getUser().setUserId(userId);
    transaction.setAmount(amount);
    transaction.setTransactionType("membership");
    transaction.setStatus(TransactionStatus.SUCCESS);
    transaction.setDescription("Annual membership fee");
    transaction.setTransactionDate(new Date());
    return transactionRepository.save(transaction);
  }

  // Apply a late fee (10% of the base amount)
  public FinancialTransaction applyLateFee(int userId, BigDecimal baseAmount) {
    BigDecimal lateFee = baseAmount.multiply(new BigDecimal("0.10"));
    FinancialTransaction transaction = new FinancialTransaction();
    transaction.setUser(new com.tennisclub.model.User());
    transaction.getUser().setUserId(userId);
    transaction.setAmount(lateFee);
    transaction.setTransactionType("late_fee");
    transaction.setStatus(TransactionStatus.SUCCESS);
    transaction.setDescription("Late payment fee");
    transaction.setTransactionDate(new Date());
    return transactionRepository.save(transaction);
  }

  // Generate a CSV report of all transactions
  public String generateReport() {
    List<FinancialTransaction> transactions = transactionRepository.findAll();
    StringBuilder report = new StringBuilder();
    report.append("TransactionId,Amount,Date,Type,Status\n");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    for (FinancialTransaction ft : transactions) {
      report.append(ft.getTransactionId()).append(",")
        .append(ft.getAmount()).append(",")
        .append(sdf.format(ft.getTransactionDate())).append(",")
        .append(ft.getTransactionType()).append(",")
        .append(ft.getStatus()).append("\n");
    }
    FinancialReport financialReport = new FinancialReport();
    financialReport.setReportData(report.toString());
    reportRepository.save(financialReport);
    return report.toString();
  }
  public FinancialTransaction payBill(int transactionId) {
    Optional<FinancialTransaction> optionalTransaction = transactionRepository.findById(transactionId);
    if (optionalTransaction.isEmpty()) {
      throw new RuntimeException("Billing record not found");
    }
    FinancialTransaction transaction = optionalTransaction.get();
    // If the status is already SUCCESS, assume it is already paid.
    if (transaction.getStatus() == TransactionStatus.SUCCESS) {
      throw new RuntimeException("This billing record is already marked as paid.");
    }
    // Mark the billing record as paid.
    transaction.setStatus(TransactionStatus.SUCCESS);
    return transactionRepository.save(transaction);
  }


  // Reconcile accounts (stub)
  public boolean reconcileAccounts() {
    return true;
  }

  // Get all transactions
  public List<FinancialTransaction> getAllBilling() {
    return transactionRepository.findAll();
  }
}
