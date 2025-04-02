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
import java.util.List;

@Service
public class FinancialService {

  @Autowired
  private FinancialTransactionRepository transactionRepository;

  @Autowired
  private FinancialReportRepository reportRepository;

  // Process a payment transaction
  public FinancialTransaction processTransaction(FinancialTransaction transaction) {
    // Check if amount is less than or equal to zero using compareTo
    if (transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
      throw new RuntimeException("Transaction amount must be greater than zero");
    }
    transaction.setStatus(TransactionStatus.PENDING);
    // Here you would integrate with a payment gateway.
    // For this example, processing is assumed to be successful.
    transaction.setStatus(TransactionStatus.SUCCESS);
    return transactionRepository.save(transaction);
  }

  // Process a refund transaction
  public FinancialTransaction processRefund(FinancialTransaction transaction) {
    if (transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
      throw new RuntimeException("Refund amount must be greater than zero");
    }
    // In a real-world scenario, verify refund conditions here.
    transaction.setStatus(TransactionStatus.SUCCESS);
    return transactionRepository.save(transaction);
  }

  // Generate a financial report in CSV format
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
    // Save the generated report into the database (optional)
    FinancialReport financialReport = new FinancialReport();
    financialReport.setReportData(report.toString());
    reportRepository.save(financialReport);
    return report.toString();
  }

  // Reconcile accounts (stub for now; extend with actual reconciliation logic)
  public boolean reconcileAccounts() {
    // For example: retrieve internal and external transactions, compare, log discrepancies.
    // In this simple implementation, we'll assume reconciliation passes.
    return true;
  }
}
