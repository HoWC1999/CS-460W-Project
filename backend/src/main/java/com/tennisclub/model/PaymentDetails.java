package com.tennisclub.model;

import java.util.Date;

public class PaymentDetails {
  private String paymentMethod;
  private String cardNumber;
  private Date expiryDate;
  private int cvv;

  // Constructors
  public PaymentDetails() {
  }

  public PaymentDetails(String paymentMethod, String cardNumber, Date expiryDate, int cvv) {
    this.paymentMethod = paymentMethod;
    this.cardNumber = cardNumber;
    this.expiryDate = expiryDate;
    this.cvv = cvv;
  }

  // Business Logic: Validate payment details (basic stub)
  public boolean validateDetails() {
    // For example: paymentMethod and cardNumber must not be empty, and expiryDate must be in the future.
    if (paymentMethod == null || paymentMethod.trim().isEmpty()) return false;
    if (cardNumber == null || cardNumber.trim().isEmpty()) return false;
    if (expiryDate == null || expiryDate.before(new Date())) return false;
    return true;
  }

  // Getters and setters
  public String getPaymentMethod() {
    return paymentMethod;
  }

  public void setPaymentMethod(String paymentMethod) {
    this.paymentMethod = paymentMethod;
  }

  public String getCardNumber() {
    return cardNumber;
  }

  public void setCardNumber(String cardNumber) {
    this.cardNumber = cardNumber;
  }

  public Date getExpiryDate() {
    return expiryDate;
  }

  public void setExpiryDate(Date expiryDate) {
    this.expiryDate = expiryDate;
  }

  public int getCvv() {
    return cvv;
  }

  public void setCvv(int cvv) {
    this.cvv = cvv;
  }
}
