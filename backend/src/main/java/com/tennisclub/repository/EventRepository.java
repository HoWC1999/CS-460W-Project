package com.tennisclub.repository;

import com.tennisclub.model.Events;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Events, Integer> {
  // Add custom queries if needed.

}
