package com.tennisclub.model.enums;

import lombok.Getter;

@Getter
public enum TransactionStatus {
  SUCCESS("Successful"),
  FAILED("Failed"),
  PENDING("Pending");

  private final String status;

  TransactionStatus(String status) {
    this.status = status;
  }

  // Helper to determine if this status is final
  public boolean isFinal() {
    return this == SUCCESS || this == FAILED;
  }
}
