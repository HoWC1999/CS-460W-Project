// src/main/java/com/tennisclub/model/EventRegistration.java
package com.tennisclub.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Represents a user's signup for a given event.
 */
@Setter
@Getter
@Entity
@Table(name = "event_registrations",
  uniqueConstraints = @UniqueConstraint(columnNames = {"event_id","user_id"}))
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class EventRegistration {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "event_id", nullable = false)
  @JsonIgnoreProperties({"hibernateLazyInitializer","handler","registrations"})
  private Events event;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  @JsonIgnoreProperties({
    "hibernateLazyInitializer","handler",
    "passwordHash",       // don’t send back the hash
    "billingPlan","status","role", // as you see fit
    "transactions","memberProfile","reservations" // avoid deep cycles
  })
  private User user;

  @Column(name = "registered_on", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date registeredOn = new Date();
}
