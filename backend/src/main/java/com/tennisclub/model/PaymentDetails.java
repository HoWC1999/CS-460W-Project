package com.tennisclub.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class PaymentDetails {
  // Getters and setters
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

}
