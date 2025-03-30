package com.tennisclub.model;

import javax.persistence.*;

@Entity
@Table(name = "member_profiles")
public class MemberProfile {

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

  // Getters and setters
  public int getProfileId() {
    return profileId;
  }

  public void setProfileId(int profileId) {
    this.profileId = profileId;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public int getGuestPassesRemaining() {
    return guestPassesRemaining;
  }

  public void setGuestPassesRemaining(int guestPassesRemaining) {
    this.guestPassesRemaining = guestPassesRemaining;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}

