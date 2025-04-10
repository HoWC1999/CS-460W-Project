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
@Table(name = "transactions")
public class FinancialTransaction {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "transaction_id")
  private int transactionId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  private User user;

  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal amount;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "transaction_date", nullable = false, columnDefinition = "datetime default CURRENT_TIMESTAMP")
  private Date transactionDate;

  @Column(name = "transaction_type", nullable = false, length = 50)
  private String transactionType;

  // We now store status as a string, but we map it to our enum
  @Column(nullable = false, length = 20)
  @Enumerated(EnumType.STRING)
  private TransactionStatus status = TransactionStatus.valueOf("PENDING");

  @Column(length = 255)
  private String description;

  @PrePersist
  protected void onCreate() {
    this.transactionDate = new Date();
    this.transactionType = " ";
  }

  public void setBillingDate(Date date) {
  }

  public void setFeeType(String membership) {
  }
}
