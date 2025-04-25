package com.tennisclub.dto;

import java.util.Date;

public class EventDTO {
  private String title;
  private String description;
  private Date eventDate;
  private Date eventTime;
  private String location;

  public EventDTO() {}

  public EventDTO(String title, String description, Date eventDate, Date eventTime, String location) {
    this.title = title;
    this.description = description;
    this.eventDate = eventDate;
    this.eventTime = eventTime;
    this.location = location;
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
