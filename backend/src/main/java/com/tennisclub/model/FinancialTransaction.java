package com.tennisclub.model;

import com.tennisclub.model.enums.TransactionStatus;
import com.tennisclub.model.enums.TransactionType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "financial_transactions")
public class FinancialTransaction {

  // Getters and setters
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int transactionId;

  private double amount;

  @Temporal(TemporalType.TIMESTAMP)
  private Date transactionDate = new Date();

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private TransactionType transactionType;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private TransactionStatus status;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User belongsTo;

  // Constructors
  public FinancialTransaction() {
  }

  public FinancialTransaction(double amount, TransactionType transactionType, TransactionStatus status, User belongsTo) {
    this.amount = amount;
    this.transactionType = transactionType;
    this.status = status;
    this.belongsTo = belongsTo;
  }

  // Business Logic: Process a payment using PaymentDetails
  public boolean processPayment(PaymentDetails details) {
    if (details.validateDetails()) {
      // In a real application, integrate with a payment gateway.
      this.status = TransactionStatus.SUCCESS;
      return true;
    } else {
      this.status = TransactionStatus.FAILED;
      return false;
    }
  }

  // Business Logic: Process a refund (stub logic)
  public boolean processRefund(int originalTransId, double refundAmount) {
    // Add refund logic here (e.g., check original transaction, apply refund rules)
    if (refundAmount > 0 && refundAmount <= this.amount) {
      this.status = TransactionStatus.SUCCESS;
      return true;
    }
    this.status = TransactionStatus.FAILED;
    return false;
  }

  // Business Logic: Adjust account with a justification (stub logic)
  public boolean adjustAccount(double adjustmentAmount, String justification) {
    // Validate justification and adjustment amount as needed.
    if (justification != null && !justification.trim().isEmpty()) {
      this.amount += adjustmentAmount;
      // You might want to log this adjustment elsewhere.
      return true;
    }
    return false;
  }

}

