package com.tennisclub.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tennisclub.model.enums.TransactionStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "billing_transactions")
public class BillingTransaction {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "billing_id")
  private int billingId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  private User user;

  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal amount;

  @Column(name = "fee_type", nullable = false, length = 50)
  private String feeType;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "billing_date", nullable = false, columnDefinition = "datetime default CURRENT_TIMESTAMP")
  private Date billingDate;

  @Column(length = 255)
  private String description;
}
