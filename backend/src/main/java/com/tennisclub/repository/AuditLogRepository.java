// src/main/java/com/tennisclub/repository/AuditLogRepository.java
package com.tennisclub.repository;

import com.tennisclub.model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}
