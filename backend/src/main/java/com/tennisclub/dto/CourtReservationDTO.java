package com.tennisclub.dto;

/**
 * Data Transfer Object for Court Reservation requests.
 * This DTO reflects the fields collected on the frontend form.
 */
public class CourtReservationDTO {

  // The full name of the user (optional)
  private String name;

  // The email address of the user
  private String email;

  // The selected court number (e.g., 1-12)
  private int court;

  // The reservation date in ISO format (e.g., "2025-04-01")
  private String date;

  // The reservation start time in HH:mm format (e.g., "08:00")
  private String time;

  public CourtReservationDTO() {}

  public CourtReservationDTO(String name, String email, int court, String date, String time) {
    this.name = name;
    this.email = email;
    this.court = court;
    this.date = date;
    this.time = time;
  }

  // Getters and setters

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public int getCourt() {
    return court;
  }

  public void setCourt(int court) {
    this.court = court;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  @Override
  public String toString() {
    return "CourtReservationDTO{" +
      "name='" + name + '\'' +
      ", email='" + email + '\'' +
      ", court=" + court +
      ", date='" + date + '\'' +
      ", time='" + time + '\'' +
      '}';
  }
}
