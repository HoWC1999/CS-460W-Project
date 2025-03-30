package com.tennisclub.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "events")
public class Event {

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

  // Getters and setters
  public int getEventId() {
    return eventId;
  }

  public void setEventId(int eventId) {
    this.eventId = eventId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Date getEventDate() {
    return eventDate;
  }

  public void setEventDate(Date eventDate) {
    this.eventDate = eventDate;
  }

  public Date getEventTime() {
    return eventTime;
  }

  public void setEventTime(Date eventTime) {
    this.eventTime = eventTime;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }
}
