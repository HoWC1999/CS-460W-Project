// src/main/java/com/tennisclub/repository/EventRegistrationRepository.java
package com.tennisclub.repository;

import com.tennisclub.model.EventRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRegistrationRepository extends JpaRepository<EventRegistration,Integer> {

  List<EventRegistration> findByEvent_EventId(int eventId);

  List<EventRegistration> findByUser_UserId(int userId);

  boolean existsByEvent_EventIdAndUser_UserId(int eventId, int userId);
}
