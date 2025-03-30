package com.tennisclub.dto;

import com.tennisclub.model.enums.TransactionType;
import java.util.Date;

public class FinancialTransactionDTO {
  private int userId;
  private double amount;
  private Date transactionDate;
  private TransactionType transactionType;

  public FinancialTransactionDTO() {}

  public FinancialTransactionDTO(int userId, double amount, Date transactionDate, TransactionType transactionType) {
    this.userId = userId;
    this.amount = amount;
    this.transactionDate = transactionDate;
    this.transactionType = transactionType;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public Date getTransactionDate() {
    return transactionDate;
  }

  public void setTransactionDate(Date transactionDate) {
    this.transactionDate = transactionDate;
  }

  public TransactionType getTransactionType() {
    return transactionType;
  }

  public void setTransactionType(TransactionType transactionType) {
    this.transactionType = transactionType;
  }
}
