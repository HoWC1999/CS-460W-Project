package com.tennisclub.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "member_profiles")
public class MemberProfile {

  // Getters and setters
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int profileId;

  private String phoneNumber;

  private String address;

  private int guestPassesRemaining;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false, unique = true)
  private User user;

  // Constructors
  public MemberProfile() {
  }

  public MemberProfile(String phoneNumber, String address, int guestPassesRemaining, User user) {
    this.phoneNumber = phoneNumber;
    this.address = address;
    this.guestPassesRemaining = guestPassesRemaining;
    this.user = user;
  }

  // Business Logic: Deduct a guest pass (if available)
  public boolean deductGuestPass() {
    if (guestPassesRemaining > 0) {
      guestPassesRemaining--;
      return true;
    }
    return false;
  }

}

