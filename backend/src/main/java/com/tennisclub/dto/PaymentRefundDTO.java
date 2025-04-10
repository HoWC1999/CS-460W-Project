// src/main/java/com/tennisclub/dto/PaymentRefundDTO.java
package com.tennisclub.dto;

public class PaymentRefundDTO {
  private int transactionId;
  private String amount; // or use BigDecimal with appropriate conversion

  public PaymentRefundDTO() {}

  public PaymentRefundDTO(int transactionId, String amount) {
    this.transactionId = transactionId;
    this.amount = amount;
  }

  public int getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(int transactionId) {
    this.transactionId = transactionId;
  }

  public String getAmount() {
    return amount;
  }

  public void setAmount(String amount) {
    this.amount = amount;
  }
}
