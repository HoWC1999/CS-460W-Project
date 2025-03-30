package com.tennisclub.model;

import com.tennisclub.model.enums.DisputeStatus;

import javax.persistence.*;

@Entity
@Table(name = "payment_disputes")
public class PaymentDispute {

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

  // Getters and setters
  public int getDisputeId() {
    return disputeId;
  }

  public void setDisputeId(int disputeId) {
    this.disputeId = disputeId;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  public DisputeStatus getStatus() {
    return status;
  }

  public void setStatus(DisputeStatus status) {
    this.status = status;
  }

  public FinancialTransaction getTransaction() {
    return transaction;
  }

  public void setTransaction(FinancialTransaction transaction) {
    this.transaction = transaction;
  }
}
