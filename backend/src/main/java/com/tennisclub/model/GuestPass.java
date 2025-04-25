// src/main/java/com/tennisclub/model/GuestPass.java
package com.tennisclub.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "guest_passes")
public class GuestPass {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "guest_pass_id")
  private int guestPassId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  private User user;

  @Column(nullable = false, precision = 10, scale = 2)
  private double price;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "purchase_date", nullable = false, columnDefinition = "datetime default CURRENT_TIMESTAMP")
  private Date purchaseDate;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "expiration_date", nullable = false)
  private Date expirationDate;

  @Column(name = "used", nullable = false)
  private boolean used;

  // Constructors
  public GuestPass() {
  }

  public GuestPass(User user, double price, Date purchaseDate, Date expirationDate, boolean used) {
    this.user = user;
    this.price = price;
    this.purchaseDate = purchaseDate;
    this.expirationDate = expirationDate;
    this.used = used;
  }
}

