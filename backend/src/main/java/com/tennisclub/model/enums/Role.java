package com.tennisclub.model.enums;

import lombok.Getter;

@Getter
public enum Role {
  MEMBER("Member"),
  ADMIN("Admin"),
  TREASURER("Treasurer");

  private final String displayName;

  Role(String displayName) {
    this.displayName = displayName;
  }

  // Example: Check if a role has administrative privileges
  public boolean isAdmin() {
    return this == ADMIN || this == TREASURER;
  }
}
