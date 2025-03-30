package com.tennisclub.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "financial_reports")
public class FinancialReport {

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

  // Getters and setters
  public int getReportId() {
    return reportId;
  }

  public void setReportId(int reportId) {
    this.reportId = reportId;
  }

  public Date getGeneratedOn() {
    return generatedOn;
  }

  public void setGeneratedOn(Date generatedOn) {
    this.generatedOn = generatedOn;
  }

  public String getReportData() {
    return reportData;
  }

  public void setReportData(String reportData) {
    this.reportData = reportData;
  }
}

