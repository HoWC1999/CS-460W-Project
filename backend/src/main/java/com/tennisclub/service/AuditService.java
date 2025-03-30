package com.tennisclub.service;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuditService {
  private List<String> logs = new ArrayList<>();

  public void logEvent(String event) {
    logs.add(event);
    System.out.println("Audit Log: " + event);
  }

  public List<String> getLogs() {
    return logs;
  }
}
