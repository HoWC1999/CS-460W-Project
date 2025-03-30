package com.tennisclub.controller;

import com.tennisclub.model.Event;
import com.tennisclub.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;
    
    @PostMapping("/create")
    public Event createEvent(@RequestBody Event event) {
        return eventService.createEvent(event);
    }
    
    @PutMapping("/update/{id}")
    public Event updateEvent(@PathVariable int id, @RequestBody Event event) {
        return eventService.updateEvent(id, event);
    }
    
    @DeleteMapping("/cancel/{id}")
    public String cancelEvent(@PathVariable int id) {
        return eventService.cancelEvent(id) ? "Event canceled" : "Cancellation failed";
    }
    
    @GetMapping("/{id}")
    public Event getEvent(@PathVariable int id) {
        return eventService.getEventById(id);
    }
}
