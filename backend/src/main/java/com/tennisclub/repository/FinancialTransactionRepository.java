package com.tennisclub.repository;

import com.tennisclub.model.FinancialTransaction;
import com.tennisclub.model.enums.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinancialTransactionRepository extends JpaRepository<FinancialTransaction, Integer> {
    List<FinancialTransaction> findByUser_UserId(int userId);
    List<FinancialTransaction> findByTransactionTypeAndStatus(String transactionType, TransactionStatus status);
    // Returns all financial transactions whose description contains the keyword "Disputed:"
    List<FinancialTransaction> findByDescriptionContaining(String disputeKeyword);
}
