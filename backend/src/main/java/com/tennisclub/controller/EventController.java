package com.tennisclub.controller;

import com.tennisclub.model.Event;
import com.tennisclub.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
public class EventController {

  @Autowired
  private EventService eventService;

  @PostMapping("/create")
  public ResponseEntity<?> createEvent(@RequestBody Event event) {
    try {
      Event createdEvent = eventService.createEvent(event);
      return ResponseEntity.ok(createdEvent);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Event creation failed: " + e.getMessage());
    }
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<?> updateEvent(@PathVariable int id, @RequestBody Event event) {
    try {
      Event updatedEvent = eventService.updateEvent(id, event);
      return ResponseEntity.ok(updatedEvent);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Event update failed: " + e.getMessage());
    }
  }

  @DeleteMapping("/cancel/{id}")
  public ResponseEntity<?> cancelEvent(@PathVariable int id) {
    try {
      boolean result = eventService.cancelEvent(id);
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
      Event event = eventService.getEventById(id);
      return ResponseEntity.ok(event);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Event not found: " + e.getMessage());
    }
  }
}
