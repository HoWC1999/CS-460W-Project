package com.tennisclub.service;

import com.tennisclub.dto.EventDTO;
import com.tennisclub.model.Events;
import com.tennisclub.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {

  @Autowired
  private EventRepository eventRepository;

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
}
