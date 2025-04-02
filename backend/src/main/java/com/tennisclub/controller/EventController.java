package com.tennisclub.controller;

import com.tennisclub.model.Events;
import com.tennisclub.repository.EventRepository;
import com.tennisclub.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Events")
public class EventController {

  @Autowired
  private EventService EventService;

  private final EventRepository EventRepository;

  private static final Logger logger = LoggerFactory.getLogger(EventController.class);

  public EventController(com.tennisclub.repository.EventRepository eventRepository) {
    EventRepository = eventRepository;
  }


  @PostMapping("/create")
  public ResponseEntity<?> createEvent(@RequestBody Events event) {
    try {
      Events createdEvent = EventService.createEvent(event);
      return ResponseEntity.ok(createdEvent);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Event creation failed: " + e.getMessage());
    }
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<?> updateEvent(@PathVariable int id, @RequestBody Events event) {
    try {
      Events updatedEvent = EventService.updateEvent(id, event);
      return ResponseEntity.ok(updatedEvent);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Event update failed: " + e.getMessage());
    }
  }

  @DeleteMapping("/cancel/{id}")
  public ResponseEntity<?> cancelEvent(@PathVariable int id) {
    try {
      boolean result = EventService.cancelEvent(id);
      if (result) {
        return ResponseEntity.ok("Event cancelled successfully.");
      } else {
        return ResponseEntity.badRequest().body("Event cancellation failed.");
      }
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Error: " + e.getMessage());
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getEvent(@PathVariable int id) {
    try {
      Events event = EventService.getEventById(id);
      return ResponseEntity.ok(event);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Event not found: " + e.getMessage());
    }
  }


  @GetMapping("/all")
  public ResponseEntity<?> getAllEvents() {
    try {
      List<Events> events = EventRepository.findAll();
      logger.info("Fetched {} events", events.size());
      return ResponseEntity.ok(events);
    } catch (Exception e) {
      logger.error("Error fetching transactions: {}", e.getMessage(), e);
      return ResponseEntity.status(500).body("Error fetching transactions: " + e.getMessage());
    }
  }
}

