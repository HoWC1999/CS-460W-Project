package com.tennisclub.repository;

import com.tennisclub.model.BillingTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillingTransactionRepository extends JpaRepository<BillingTransaction, Integer> {

}
