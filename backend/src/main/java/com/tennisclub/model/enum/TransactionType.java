package com.tennisclub.model.enums;

public enum TransactionType {
  PAYMENT("Payment"),
  REFUND("Refund"),
  ADJUSTMENT("Adjustment");

  private final String description;

  TransactionType(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}
