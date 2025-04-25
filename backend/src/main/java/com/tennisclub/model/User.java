package com.tennisclub.model;

import com.tennisclub.model.enums.BillingPlan;
import com.tennisclub.model.enums.Role;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "users")
public class User {

  @Transient
  private String password;
  // Getters and setters
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int userId;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false)
  private String passwordHash;

  @Column(nullable = false, unique = true)
  private String email;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Role role;

  @Column(nullable = false)
  private String status; // e.g., "Active", "Terminated"

  @Column(nullable = true)
  private String phoneNumber;

  @Column(nullable = true)
  private String address;

  @Column(nullable = true)
  private String fullName;

  @Column(name = "billing_plan", nullable = false)
  @Enumerated(EnumType.STRING)
  private BillingPlan billingPlan = BillingPlan.MONTHLY;

  // Constructors
  public User() {
  }

  public User(String username, String passwordHash, String email, Role role, String status) {
    this.username = username;
    this.passwordHash = passwordHash;
    this.email = email;
    this.role = role;
    this.status = status;
  }
}
