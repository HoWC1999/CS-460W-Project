// src/main/java/com/tennisclub/model/AuditLog.java
package com.tennisclub.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "audit_logs")
@Getter
@Setter
@NoArgsConstructor
public class AuditLog {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "log_id")
  private int id;

  @Column(nullable = false)
  private String username;

  @Column(nullable = false)
  private String action;

  @Column(length = 4000)
  private String details;

  @Column(nullable = false)
  private Instant timestamp = Instant.now();
}
