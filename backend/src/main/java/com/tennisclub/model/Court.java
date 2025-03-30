package com.tennisclub.model;

import javax.persistence.*;

@Entity
@Table(name = "courts")
public class Court {

  @Id
  private int courtNumber;

  private boolean isAvailable = true;

  // Constructors
  public Court() {
  }

  public Court(int courtNumber, boolean isAvailable) {
    this.courtNumber = courtNumber;
    this.isAvailable = isAvailable;
  }

  // Business Logic: Check availability (this stub could be enhanced to check schedules)
  public boolean checkAvailability() {
    return isAvailable;
  }

  // Business Logic: Update availability status
  public void updateAvailability(boolean status) {
    this.isAvailable = status;
  }

  // Getters and setters
  public int getCourtNumber() {
    return courtNumber;
  }

  public void setCourtNumber(int courtNumber) {
    this.courtNumber = courtNumber;
  }

  public boolean isAvailable() {
    return isAvailable;
  }

  public void setAvailable(boolean available) {
    isAvailable = available;
  }
}
