package com.tennisclub.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "financial_reports")
public class FinancialReport {

  // Getters and setters
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int reportId;

  @Temporal(TemporalType.TIMESTAMP)
  private Date generatedOn = new Date();

  @Lob
  private String reportData; // Could store CSV, JSON, or plain text

  // Business Logic: Example method to update report data
  public void updateReportData(String newData) {
    this.reportData = newData;
    this.generatedOn = new Date();
  }

}

