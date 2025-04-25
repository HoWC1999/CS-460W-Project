package com.tennisclub.model.enums;

import lombok.Getter;

@Getter
public enum TransactionType {
  PAYMENT("Payment"),
  REFUND("Refund"),
  ADJUSTMENT("Adjustment");

  private final String description;

  TransactionType(String description) {
    this.description = description;
  }

}
