package com.tennisclub.controller;

import com.tennisclub.model.AuditLog;
import com.tennisclub.service.AuditLogService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/audit")
public class AdminAuditController {

  private final AuditLogService auditLogService;

  public AdminAuditController(AuditLogService auditLogService) {
    this.auditLogService = auditLogService;
  }

  /**
   * Returns all audit log entries. ADMIN only.
   */
  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/logs")
  public List<AuditLog> getAllAuditLogs() {
    return auditLogService.getAllLogs();
  }
}
