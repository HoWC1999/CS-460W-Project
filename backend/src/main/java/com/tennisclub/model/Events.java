package com.tennisclub.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "events")
public class Events {

  // Getters and setters
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "event_id")
  private int eventId;

  @Column(name = "title", nullable = false)
  private String title;

  @Lob
  @Column(name = "description")
  private String description;

  @Temporal(TemporalType.DATE)
  @Column(name = "event_date", nullable = false)
  private Date eventDate;

  @Temporal(TemporalType.TIME)
  @Column(name = "event_time", nullable = false)
  private Date eventTime;

  @Column(name = "location")
  private String location;

}
