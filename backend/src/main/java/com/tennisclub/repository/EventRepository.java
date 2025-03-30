package com.tennisclub.repository;

import com.tennisclub.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
  // Add custom queries if needed.
}
