package com.tennisclub.model.enums;

import lombok.Getter;

@Getter
public enum DisputeStatus {
  OPEN("Open"),
  RESOLVED("Resolved"),
  REJECTED("Rejected");

  private final String description;

  DisputeStatus(String description) {
    this.description = description;
  }

  // Helper to check if the dispute is closed
  public boolean isClosed() {
    return this == RESOLVED || this == REJECTED;
  }
}
