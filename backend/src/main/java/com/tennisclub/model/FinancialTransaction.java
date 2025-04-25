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

  // The status is mapped as an enum
  @Column(nullable = false, length = 20)
  @Enumerated(EnumType.STRING)
  private TransactionStatus status = TransactionStatus.PENDING;

  @Column(length = 255)
  private String description;

  // Optional: Automatically set the transaction date if not provided.
  // Remove or modify this method if you want to allow explicit setting.
  @PrePersist
  protected void onCreate() {
    if (this.transactionDate == null) {
      this.transactionDate = new Date();
    }
  }

  /**
   * Sets the billing date for the transaction.
   * In this entity, the billing date maps to the transaction date.
   *
   * @param date The date when billing occurs.
   */
  public void setBillingDate(Date date) {
    this.transactionDate = date;
  }

  /**
   * Sets the fee type (e.g., "membership", "late_fee", etc.)
   *
   * @param feeType The fee type to set.
   */
  public void setFeeType(String feeType) {
    this.transactionType = feeType;
  }

  /**
   * Retrieves the fee type.
   *
   * @return The fee type.
   */
  public String getFeeType() {
    return this.transactionType;
  }
}

