package com.tennisclub.repository;

import com.tennisclub.model.FinancialTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinancialTransactionRepository extends JpaRepository<FinancialTransaction, Integer> {
  List<FinancialTransaction> findByUser_UserId(int userId);

}
