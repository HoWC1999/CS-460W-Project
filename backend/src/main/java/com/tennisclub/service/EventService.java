package com.tennisclub.service;

import com.tennisclub.dto.EventDTO;
import com.tennisclub.dto.RegistrationRequest;
import com.tennisclub.model.EventRegistration;
import com.tennisclub.model.Events;
import com.tennisclub.model.User;
import com.tennisclub.repository.EventRegistrationRepository;
import com.tennisclub.repository.EventRepository;
import com.tennisclub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class EventService {

  @Autowired
  private EventRepository eventRepository;

  @Autowired
  private EventRegistrationRepository registrationRepository;

  @Autowired
  private UserRepository userRepository;

  public Events createEvent(EventDTO dto) {
    if (dto.getTitle() == null || dto.getTitle().isBlank()) {
      throw new RuntimeException("Event title is required");
    }
    // Map DTO → Entity
    Events event = new Events();
    event.setTitle(dto.getTitle());
    event.setDescription(dto.getDescription());
    event.setEventDate(dto.getEventDate());
    event.setEventTime(dto.getEventTime());
    event.setLocation(dto.getLocation());
    // Additional validations could go here
    return eventRepository.save(event);
  }

  public Events updateEvent(int eventId, EventDTO dto) {
    Events event = eventRepository.findById(eventId)
      .orElseThrow(() -> new RuntimeException("Event not found"));

    // Patch only non-null DTO fields onto the entity
    if (dto.getTitle() != null) {
      event.setTitle(dto.getTitle());
    }
    if (dto.getDescription() != null) {
      event.setDescription(dto.getDescription());
    }
    if (dto.getEventDate() != null) {
      event.setEventDate(dto.getEventDate());
    }
    if (dto.getEventTime() != null) {
      event.setEventTime(dto.getEventTime());
    }
    if (dto.getLocation() != null) {
      event.setLocation(dto.getLocation());
    }

    return eventRepository.save(event);
  }

  public boolean cancelEvent(int eventId) {
    if (!eventRepository.existsById(eventId)) {
      throw new RuntimeException("Event not found");
    }
    eventRepository.deleteById(eventId);
    return true;
  }

  public Events getEventById(int eventId) {
    return eventRepository.findById(eventId)
      .orElseThrow(() -> new RuntimeException("Event not found"));
  }
  /**
   * Register a user for a given event.
   * @param eventId the target event
   * @param req      contains the userId
   * @return created EventRegistration
   */
  @Transactional
  public EventRegistration registerUserForEvent(int eventId, RegistrationRequest req) {
    Events event = eventRepository.findById(eventId)
      .orElseThrow(() -> new RuntimeException("Event not found"));

    User user = userRepository.findById(req.getUserId())
      .orElseThrow(() -> new RuntimeException("User not found"));

    // Prevent double‐registration
    if (registrationRepository.existsByEvent_EventIdAndUser_UserId(eventId, req.getUserId())) {
      throw new RuntimeException("User already registered for this event");
    }

    EventRegistration reg = new EventRegistration();
    reg.setEvent(event);
    reg.setUser(user);
    // reg.registeredOn is auto‐set
    return registrationRepository.save(reg);
  }

  /**
   * List all registrations for an event.
   */
  public List<EventRegistration> getRegistrationsForEvent(int eventId) {
    return registrationRepository.findByEvent_EventId(eventId);
  }

}
