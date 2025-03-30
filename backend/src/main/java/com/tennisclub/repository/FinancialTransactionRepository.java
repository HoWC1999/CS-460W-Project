package com.tennisclub.repository;

import com.tennisclub.model.FinancialTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinancialTransactionRepository extends JpaRepository<FinancialTransaction, Integer> {
  // Add custom queries if needed.
}
