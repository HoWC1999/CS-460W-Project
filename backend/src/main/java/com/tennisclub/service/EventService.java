package com.tennisclub.service;

import com.tennisclub.model.Event;
import com.tennisclub.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class EventService {

  @Autowired
  private EventRepository eventRepository;

  public Event createEvent(Event event) {
    if(event.getTitle() == null || event.getTitle().isEmpty()) {
      throw new RuntimeException("Event title is required");
    }
    // Additional validations can be added here.
    return eventRepository.save(event);
  }

  public Event updateEvent(int eventId, Event newEventData) {
    Optional<Event> optionalEvent = eventRepository.findById(eventId);
    if(!optionalEvent.isPresent()) {
      throw new RuntimeException("Event not found");
    }
    Event event = optionalEvent.get();
    if(newEventData.getTitle() != null) {
      event.setTitle(newEventData.getTitle());
    }
    if(newEventData.getDescription() != null) {
      event.setDescription(newEventData.getDescription());
    }
    if(newEventData.getEventDate() != null) {
      event.setEventDate(newEventData.getEventDate());
    }
    if(newEventData.getEventTime() != null) {
      event.setEventTime(newEventData.getEventTime());
    }
    if(newEventData.getLocation() != null) {
      event.setLocation(newEventData.getLocation());
    }
    return eventRepository.save(event);
  }

  public boolean cancelEvent(int eventId) {
    if(!eventRepository.existsById(eventId)) {
      throw new RuntimeException("Event not found");
    }
    eventRepository.deleteById(eventId);
    return true;
  }

  public Event getEventById(int eventId) {
    return eventRepository.findById(eventId)
      .orElseThrow(() -> new RuntimeException("Event not found"));
  }
}
