package com.tennisclub.repository;

import com.tennisclub.model.FinancialReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinancialReportRepository extends JpaRepository<FinancialReport, Integer> {
  // Add custom queries if needed.
}
