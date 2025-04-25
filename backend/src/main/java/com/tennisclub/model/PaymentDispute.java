package com.tennisclub.model;

import com.tennisclub.model.enums.DisputeStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "payment_disputes")
public class PaymentDispute {

  // Getters and setters
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int disputeId;

  private String reason;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private DisputeStatus status = DisputeStatus.OPEN;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "transaction_id", nullable = false)
  private FinancialTransaction transaction;

  // Business Logic: Resolve dispute with a provided resolution note (stub)
  public boolean resolveDispute(String resolution) {
    if (resolution != null && !resolution.trim().isEmpty()) {
      this.status = DisputeStatus.RESOLVED;
      return true;
    }
    return false;
  }

}
