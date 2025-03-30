package com.tennisclub.model;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

@Entity
@Table(name = "court_reservations")
public class CourtReservation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int reservationId;

  @Temporal(TemporalType.DATE)
  private Date reservationDate;

  private Time startTime;

  private Time endTime;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User bookedBy;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "court_number", nullable = false)
  private Court court;

  // Constructors
  public CourtReservation() {
  }

  public CourtReservation(Date reservationDate, Time startTime, Time endTime, User bookedBy, Court court) {
    this.reservationDate = reservationDate;
    this.startTime = startTime;
    this.endTime = endTime;
    this.bookedBy = bookedBy;
    this.court = court;
  }

  // Business Logic: Validate reservation times (e.g., startTime should be before endTime)
  public boolean isValidReservation() {
    return startTime != null && endTime != null && startTime.before(endTime);
  }

  // Getters and setters
  public int getReservationId() {
    return reservationId;
  }

  public void setReservationId(int reservationId) {
    this.reservationId = reservationId;
  }

  public Date getReservationDate() {
    return reservationDate;
  }

  public void setReservationDate(Date reservationDate) {
    this.reservationDate = reservationDate;
  }

  public Time getStartTime() {
    return startTime;
  }

  public void setStartTime(Time startTime) {
    this.startTime = startTime;
  }

  public Time getEndTime() {
    return endTime;
  }

  public void setEndTime(Time endTime) {
    this.endTime = endTime;
  }

  public User getBookedBy() {
    return bookedBy;
  }

  public void setBookedBy(User bookedBy) {
    this.bookedBy = bookedBy;
  }

  public Court getCourt() {
    return court;
  }

  public
