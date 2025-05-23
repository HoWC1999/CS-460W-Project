package com.tennisclub.controller;

import com.tennisclub.dto.EventDTO;
import com.tennisclub.dto.RegistrationRequest;
import com.tennisclub.model.EventRegistration;
import com.tennisclub.model.Events;
import com.tennisclub.repository.EventRepository;
import com.tennisclub.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

  private static final Logger logger = LoggerFactory.getLogger(EventController.class);

  private final EventService eventService;
  private final EventRepository eventRepository;

  public EventController(EventService eventService,
                         EventRepository eventRepository) {
    this.eventService = eventService;
    this.eventRepository = eventRepository;
  }

  @PostMapping("/create")
  public ResponseEntity<?> createEvent(@RequestBody EventDTO dto) {
    try {
      Events created = eventService.createEvent(dto);
      return ResponseEntity.ok(created);
    } catch (Exception e) {
      return ResponseEntity
        .badRequest()
        .body("Event creation failed: " + e.getMessage());
    }
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<?> updateEvent(@PathVariable int id,
                                       @RequestBody EventDTO dto) {
    try {
      Events updated = eventService.updateEvent(id, dto);
      return ResponseEntity.ok(updated);
    } catch (Exception e) {
      return ResponseEntity
        .badRequest()
        .body("Event update failed: " + e.getMessage());
    }
  }

  @DeleteMapping("/cancel/{id}")
  public ResponseEntity<?> cancelEvent(@PathVariable int id) {
    try {
      boolean deleted = eventService.cancelEvent(id);
      if (deleted) {
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
      Events event = eventService.getEventById(id);
      return ResponseEntity.ok(event);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Event not found: " + e.getMessage());
    }
  }

  @GetMapping("/all")
  public ResponseEntity<?> getAllEvents() {
    try {
      List<Events> events = eventRepository.findAll();
      logger.info("Fetched {} events", events.size());
      return ResponseEntity.ok(events);
    } catch (Exception e) {
      logger.error("Error fetching events: {}", e.getMessage(), e);
      return ResponseEntity
        .status(500)
        .body("Error fetching events: " + e.getMessage());
    }
  }
  /**
   * POST /api/events/{id}/signup
   * Body: { "userId": 123 }
   */
  @PostMapping("/{id}/signup")
  public ResponseEntity<?> signup(@PathVariable("id") int eventId,
                                  @RequestBody RegistrationRequest req) {
    try {
      EventRegistration registration =
        eventService.registerUserForEvent(eventId, req);
      logger.info("User {} signed up for event {}", req.getUserId(), eventId);
      return ResponseEntity.ok(registration);
    } catch (Exception e) {
      logger.error("Signup failed for user {} on event {}: {}",
        req.getUserId(), eventId, e.getMessage());
      return ResponseEntity
        .badRequest()
        .body("Signup failed: " + e.getMessage());
    }
  }

  /**
   * GET /api/events/{id}/registrations
   * List all signups for an event.
   */
  @GetMapping("/{id}/registrations")
  public ResponseEntity<?> listRegistrations(@PathVariable("id") int eventId) {
    try {
      List<EventRegistration> regs =
        eventService.getRegistrationsForEvent(eventId);
      return ResponseEntity.ok(regs);
    } catch (Exception e) {
      return ResponseEntity
        .status(500)
        .body("Could not fetch registrations: " + e.getMessage());
    }
  }
}
