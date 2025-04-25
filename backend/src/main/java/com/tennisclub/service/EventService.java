package com.tennisclub.service;

import com.tennisclub.model.Events;
import com.tennisclub.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class EventService {

  @Autowired
  private EventRepository eventRepository;


  public Events createEvent(Events event) {
    if(event.getTitle() == null || event.getTitle().isEmpty()) {
      throw new RuntimeException("Event title is required");
    }
    // Additional validations can be added here.
    return eventRepository.save(event);
  }

  public Events updateEvent(int eventId, Events newEventData) {
    Optional<Events> optionalEvent = eventRepository.findById(eventId);
    if(!optionalEvent.isPresent()) {
      throw new RuntimeException("Event not found");
    }
    Events event = optionalEvent.get();
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

  public Events getEventById(int eventId) {
    return eventRepository.findById(eventId)
      .orElseThrow(() -> new RuntimeException("Event not found"));
  }
}
