package com.tennisclub.repository;

import com.tennisclub.model.Court;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourtRepository extends JpaRepository<Court, Integer> {
  // Add custom queries if needed.
}
