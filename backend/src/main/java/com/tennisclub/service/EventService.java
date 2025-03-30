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
    return eventRepository.save(event);
  }

  public Event updateEvent(int eventId, Event newEventData) {
    Optional<Event> optionalEvent = eventRepository.findById(eventId);
    if(optionalEvent.isPresent()){
      Event event = optionalEvent.get();
      event.setTitle(newEventData.getTitle());
      event.setDescription(newEventData.getDescription());
      event.setEventDate(newEventData.getEventDate());
      event.setEventTime(newEventData.getEventTime());
      event.setLocation(newEventData.getLocation());
      return eventRepository.save(event);
    }
    throw new RuntimeException("Event not found");
  }

  public boolean cancelEvent(int eventId) {
    if(eventRepository.existsById(eventId)){
      eventRepository.deleteById(eventId);
      return true;
    }
    return false;
  }

  public Event getEventById(int eventId) {
    return eventRepository.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found"));
  }
}
