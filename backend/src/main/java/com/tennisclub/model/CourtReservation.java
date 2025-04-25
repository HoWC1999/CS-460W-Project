package com.tennisclub.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "court_reservations")
public class CourtReservation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int reservationId;

  @Temporal(TemporalType.DATE)
  @Column(nullable = false)
  private Date reservationDate;

  @Column(nullable = false)
  private Time startTime;

  @Column(nullable = false)
  private Time endTime;

  // Assuming a many-to-one relationship with User
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User bookedBy;

  @Column(name = "court_number", nullable = false)
  private int courtNumber;

  public CourtReservation() {
  }

  // Getters and setters

}
