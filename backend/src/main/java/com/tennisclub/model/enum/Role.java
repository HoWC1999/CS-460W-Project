package com.tennisclub.model.enums;

public enum Role {
  MEMBER("Member"),
  ADMIN("Admin"),
  TREASURER("Treasurer");

  private final String displayName;

  Role(String displayName) {
    this.displayName = displayName;
  }

  public String getDisplayName() {
    return displayName;
  }

  // Example: Check if a role has administrative privileges
  public boolean isAdmin() {
    return this == ADMIN || this == TREASURER;
  }
}
