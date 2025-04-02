package com.tennisclub.dto;

public class RoleAssignmentDTO {
  private String role;

  public RoleAssignmentDTO() {}

  public RoleAssignmentDTO(String role) {
    this.role = role;
  }

  public String getRole() {
    return role;
  }
  public void setRole(String role) {
    this.role = role;
  }
}
