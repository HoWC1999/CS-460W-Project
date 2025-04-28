// src/test/java/com/tennisclub/service/EventServiceTest.java
package com.tennisclub.service;

import com.tennisclub.dto.EventDTO;
import com.tennisclub.model.Events;
import com.tennisclub.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

  @Mock
  private EventRepository eventRepository;

  @InjectMocks
  private EventService eventService;

  private Events existingEvent;
  private Date someDate;
  private LocalTime someTime;

  @BeforeEach
  void setUp() {
    someDate = new Date();
    someTime = LocalTime.of(14, 30);

    existingEvent = new Events();
    existingEvent.setTitle("Orig Title");
    existingEvent.setDescription("Orig Desc");
    existingEvent.setEventDate(someDate);
    existingEvent.setEventTime(Time.valueOf(someTime));
    existingEvent.setLocation("Orig Location");
  }

  // createEvent tests

  @Test
  void createEvent_withValidTitle_savesAndReturns() {
    // Arrange: build DTO
    EventDTO dto = new EventDTO();
    dto.setTitle("My Event");
    dto.setDescription("Some Description");
    dto.setEventDate(someDate);
    dto.setEventTime(Time.valueOf(someTime));
    dto.setLocation("My Location");

    // Stub repository to return an entity
    Events saved = new Events();
    saved.setTitle(dto.getTitle());
    when(eventRepository.save(any(Events.class))).thenReturn(saved);

    // Act
    Events result = eventService.createEvent(dto);

    // Assert
    assertSame(saved, result);
    verify(eventRepository).save(any(Events.class));
  }

  @Test
  void createEvent_withNullOrEmptyTitle_throws() {
    // Null title
    EventDTO dto1 = new EventDTO();
    dto1.setTitle(null);
    assertThrows(RuntimeException.class, () -> eventService.createEvent(dto1));

    // Empty title
    EventDTO dto2 = new EventDTO();
    dto2.setTitle("");
    assertThrows(RuntimeException.class, () -> eventService.createEvent(dto2));
  }

  // updateEvent tests

  @Test
  void updateEvent_existingEvent_updatesOnlyNonNullFields() {
    when(eventRepository.findById(1)).thenReturn(Optional.of(existingEvent));
    when(eventRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

    // Build DTO: only some fields non-null
    EventDTO dto = new EventDTO();
    dto.setTitle("New Title");
    dto.setDescription(null);  // should leave original
    dto.setEventDate(new Date(someDate.getTime() + 86_400_000L)); // +1 day
    dto.setEventTime(Time.valueOf(LocalTime.of(16, 0)));
    dto.setLocation("New Location");

    // Act
    Events result = eventService.updateEvent(1, dto);

    // Verify fields
    assertEquals("New Title", result.getTitle());
    assertEquals("Orig Desc", result.getDescription(), "description unchanged");
    assertNotEquals(someDate, result.getEventDate());
    assertEquals(Time.valueOf(LocalTime.of(16, 0)), result.getEventTime());
    assertEquals("New Location", result.getLocation());

    // Verify save called with patched entity
    ArgumentCaptor<Events> cap = ArgumentCaptor.forClass(Events.class);
    verify(eventRepository).save(cap.capture());
    assertSame(result, cap.getValue());
  }

  @Test
  void updateEvent_nonExisting_throws() {
    when(eventRepository.findById(99)).thenReturn(Optional.empty());
    EventDTO dto = new EventDTO();
    assertThrows(RuntimeException.class, () -> eventService.updateEvent(99, dto));
  }

  // cancelEvent tests

  @Test
  void cancelEvent_existing_returnsTrueAndDeletes() {
    when(eventRepository.existsById(2)).thenReturn(true);
    boolean result = eventService.cancelEvent(2);
    assertTrue(result);
    verify(eventRepository).deleteById(2);
  }

  @Test
  void cancelEvent_nonExisting_throws() {
    when(eventRepository.existsById(3)).thenReturn(false);
    assertThrows(RuntimeException.class, () -> eventService.cancelEvent(3));
  }

  // getEventById tests

  @Test
  void getEventById_existing_returnsEvent() {
    when(eventRepository.findById(1)).thenReturn(Optional.of(existingEvent));
    Events result = eventService.getEventById(1);
    assertSame(existingEvent, result);
  }

  @Test
  void getEventById_nonExisting_throws() {
    when(eventRepository.findById(4)).thenReturn(Optional.empty());
    assertThrows(RuntimeException.class, () -> eventService.getEventById(4));
  }
}
