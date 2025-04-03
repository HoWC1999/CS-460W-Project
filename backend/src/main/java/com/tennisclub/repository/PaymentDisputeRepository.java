package com.tennisclub.repository;

import com.tennisclub.model.PaymentDispute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentDisputeRepository extends JpaRepository<PaymentDispute, Integer> {
  // Add custom queries if needed.
}
