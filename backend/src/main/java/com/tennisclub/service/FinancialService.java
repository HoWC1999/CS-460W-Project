package com.tennisclub.service;

import com.tennisclub.model.FinancialTransaction;
import com.tennisclub.model.enums.TransactionStatus;
import com.tennisclub.repository.FinancialTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class FinancialService {

  @Autowired
  private FinancialTransactionRepository transactionRepository;

  public FinancialTransaction processTransaction(FinancialTransaction transaction) {
    // Here you would add validation and payment processing logic
    transaction.setStatus(TransactionStatus.SUCCESS);
    return transactionRepository.save(transaction);
  }

  public FinancialTransaction processRefund(FinancialTransaction transaction) {
    // Process refund logic here
    transaction.setStatus(TransactionStatus.SUCCESS);
    return transactionRepository.save(transaction);
  }

  public String generateReport() {
    // Generate a simple CSV report for demonstration
    List<FinancialTransaction> transactions = transactionRepository.findAll();
    StringBuilder report = new StringBuilder();
    report.append("TransactionId,Amount,Date,Type,Status\n");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    for(FinancialTransaction ft : transactions){
      report.append(ft.getTransactionId()).append(",")
        .append(ft.getAmount()).append(",")
        .append(sdf.format(ft.getTransactionDate())).append(",")
        .append(ft.getTransactionType()).append(",")
        .append(ft.getStatus()).append("\n");
    }
    return report.toString();
  }

  public boolean reconcileAccounts() {
    // Implement reconciliation logic as needed
    return true;
  }
}
