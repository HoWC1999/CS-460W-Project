package com.tennisclub.dto;

import java.util.Date;
import java.util.Map;

public class ReportCriteriaDTO {
  private Date startDate;
  private Date endDate;
  private Map<String, Object> filters;

  public ReportCriteriaDTO() {}

  public ReportCriteriaDTO(Date startDate, Date endDate, Map<String, Object> filters) {
    this.startDate = startDate;
    this.endDate = endDate;
    this.filters = filters;
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

  public Map<String, Object> getFilters() {
    return filters;
  }

  public void setFilters(Map<String, Object> filters) {
    this.filters = filters;
  }
}
