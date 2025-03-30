package com.tennisclub.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "reconciliations")
public class Reconciliation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int reconciliationId;

  @ElementCollection
  @CollectionTable(name = "reconciliation_discrepancies", joinColumns = @JoinColumn(name = "reconciliation_id"))
  @Column(name = "discrepancy")
  private List<String> discrepancies = new ArrayList<>();

  // Constructors
  public Reconciliation() {
  }

  public Reconciliation(List<String> discrepancies) {
    this.discrepancies = discrepancies;
  }

  // Detailed reconciliation logic:
  // This method compares internal and external financial transactions,
  // logs discrepancies if no matching record is found or if details do not match,
  // and returns the list of discrepancies.
  public List<String> reconcileTransactions(List<FinancialTransaction> internalTransactions,
                                            List<FinancialTransaction> externalTransactions) {
    // Clear previous discrepancies if any
    discrepancies.clear();

    // Tolerance for comparing amounts (e.g., $0.01 difference allowed)
    final double TOLERANCE = 0.01;

    // Check for each internal transaction whether a matching external record exists
    for (FinancialTransaction internal : internalTransactions) {
      boolean foundMatch = false;
      for (FinancialTransaction external : externalTransactions) {
        if (matches(internal, external, TOLERANCE)) {
          foundMatch = true;
          break;
        }
      }
      if (!foundMatch) {
        discrepancies.add("Missing external record for internal transaction ID "
          + internal.getTransactionId() + " (Type: " + internal.getTransactionType() +
          ", Amount: " + internal.getAmount() + ", Date: " + internal.getTransactionDate() + ")");
      }
    }

    // Check for external transactions that have no matching internal record
    for (FinancialTransaction external : externalTransactions) {
      boolean foundMatch = false;
      for (FinancialTransaction internal : internalTransactions) {
        if (matches(internal, external, TOLERANCE)) {
          foundMatch = true;
          break;
        }
      }
      if (!foundMatch) {
        discrepancies.add("Missing internal record for external transaction (Amount: "
          + external.getAmount() + ", Type: " + external.getTransactionType() +
          ", Date: " + external.getTransactionDate() + ")");
      }
    }

    return discrepancies;
  }

  // Helper method to determine if two transactions match within tolerance
  private boolean matches(FinancialTransaction t1, FinancialTransaction t2, double tolerance) {
    if (t1.getTransactionType() != t2.getTransactionType()) {
      return false;
    }
    if (Math.abs(t1.getAmount() - t2.getAmount()) > tolerance) {
      return false;
    }
    // Here, we check date equality by comparing the date parts.
    // Depending on your needs, you may wish to allow a small window (e.g., same day).
    // For simplicity, we compare full Date objects.
    if (!t1.getTransactionDate().equals(t2.getTransactionDate())) {
      return false;
    }
    return true;
  }

  // Getters and setters
  public int getReconciliationId() {
    return reconciliationId;
  }

  public void setReconciliationId(int reconciliationId) {
    this.reconciliationId = reconciliationId;
  }

  public List<String> getDiscrepancies() {
    return discrepancies;
  }

  public void setDiscrepancies(List<String> discrepancies) {
    this.discrepancies = discrepancies;
  }
}

