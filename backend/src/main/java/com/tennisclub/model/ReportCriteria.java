package com.tennisclub.model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "report_criteria")
public class ReportCriteria {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Temporal(TemporalType.DATE)
  private Date startDate;

  @Temporal(TemporalType.DATE)
  private Date endDate;

  @ElementCollection
  @CollectionTable(name = "criteria_filters", joinColumns = @JoinColumn(name = "criteria_id"))
  @MapKeyColumn(name = "filter_key")
  @Column(name = "filter_value")
  private Map<String, String> filters = new HashMap<>();

  // Business Logic: Add or update a filter
  public void addOrUpdateFilter(String key, String value) {
    this.filters.put(key, value);
  }

  // Getters and setters
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public Map<String, String> getFilters() {
    return filters;
  }

  public void setFilters(Map<String, String> filters) {
    this.filters = filters;
  }
}
