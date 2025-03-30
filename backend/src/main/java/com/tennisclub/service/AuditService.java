package com.tennisclub.service;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuditService {

  private final List<String> auditLogs = new ArrayList<>();

  // Log an event with a timestamp
  public void logEvent(String event) {
    String logEntry = "[" + new java.util.Date() + "] " + event;
    auditLogs.add(logEntry);
    System.out.println("Audit log: " + logEntry);
  }

  // Retrieve a copy of all audit logs
  public List<String> getAuditLogs() {
    return new ArrayList<>(auditLogs);
  }
}
