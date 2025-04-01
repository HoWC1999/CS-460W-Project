package com.tennisclub.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "events")
public class Event {

  // Getters and setters
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int eventId;

  @Column(nullable = false)
  private String title;

  @Lob
  private String description;

  @Temporal(TemporalType.DATE)
  @Column(nullable = false)
  private Date eventDate;

  @Temporal(TemporalType.TIME)
  @Column(nullable = false)
  private Date eventTime;

  private String location;

  // Constructors
  public Event() {
  }

  public Event(String title, String description, Date eventDate, Date eventTime, String location) {
    this.title = title;
    this.description = description;
    this.eventDate = eventDate;
    this.eventTime = eventTime;
    this.location = location;
  }

  // Business Logic: Check if the event is upcoming
  public boolean isUpcoming() {
    return eventDate.after(new Date());
  }

}
