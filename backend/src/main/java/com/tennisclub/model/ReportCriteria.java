package com.tennisclub.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
@Entity
@Table(name = "report_criteria")
public class ReportCriteria {

  // Getters and setters
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

}
