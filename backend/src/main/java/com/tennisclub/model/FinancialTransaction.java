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
@Table(name = "financial_transactions")
public class FinancialTransaction {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "transaction_id")
  private int transactionId;

  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal amount;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "transaction_date", nullable = false, columnDefinition = "datetime default CURRENT_TIMESTAMP")
  private Date transactionDate;

  @Column(name = "transaction_type", nullable = false, length = 20)
  private String transactionType;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private TransactionStatus status;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  private User user;
}
