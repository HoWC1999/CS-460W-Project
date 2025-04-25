package com.tennisclub.service;

import com.tennisclub.model.AuditLog;
import com.tennisclub.repository.AuditLogRepository;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.List;

@Service
public class AuditLogService {
  private final AuditLogRepository auditLogRepository;

  public AuditLogService(AuditLogRepository auditLogRepository) {
    this.auditLogRepository = auditLogRepository;
  }

  /**
   * Persist a new audit entry
   */
  public AuditLog logEvent(String username, String action, String details) {
    AuditLog entry = new AuditLog();
    entry.setUsername(username);
    entry.setAction(action);
    entry.setDetails(details);
    entry.setTimestamp(Instant.now());
    return auditLogRepository.save(entry);
  }

  /**
   * Fetch all persisted audit entries
   */
  public List<AuditLog> getAllLogs() {
    return auditLogRepository.findAll();
  }
}
