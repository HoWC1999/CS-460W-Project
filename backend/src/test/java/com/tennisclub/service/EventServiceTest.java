package com.tennisclub.service;

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
        Events newEvent = new Events();
        newEvent.setTitle("My Event");

        when(eventRepository.save(newEvent)).thenReturn(newEvent);

        Events result = eventService.createEvent(newEvent);

        assertSame(newEvent, result);
        verify(eventRepository).save(newEvent);
    }

    @Test
    void createEvent_withNullOrEmptyTitle_throws() {
        Events e1 = new Events();
        e1.setTitle(null);
        assertThrows(RuntimeException.class, () -> eventService.createEvent(e1));

        Events e2 = new Events();
        e2.setTitle("");
        assertThrows(RuntimeException.class, () -> eventService.createEvent(e2));
    }

    // updateEvent tests

    @Test
    void updateEvent_existingEvent_updatesOnlyNonNullFields() {
        when(eventRepository.findById(1)).thenReturn(Optional.of(existingEvent));
        when(eventRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        Events update = new Events();
        update.setTitle("New Title");
        update.setDescription(null);
        update.setEventDate(new Date(someDate.getTime() + 86400000L)); // +1 day
        update.setEventTime(Time.valueOf(LocalTime.of(16, 0)));
        update.setLocation("New Location");

        Events result = eventService.updateEvent(1, update);

        // verify fields
        assertEquals("New Title",       result.getTitle());
        assertEquals("Orig Desc",       result.getDescription(), "description unchanged");
        assertNotEquals(someDate,       result.getEventDate());
        assertEquals(Time.valueOf(LocalTime.of(16,0)), result.getEventTime());
        assertEquals("New Location",    result.getLocation());

        // verify save called with the patched entity
        ArgumentCaptor<Events> captor = ArgumentCaptor.forClass(Events.class);
        verify(eventRepository).save(captor.capture());
        assertSame(result, captor.getValue());
    }

    @Test
    void updateEvent_nonExisting_throws() {
        when(eventRepository.findById(99)).thenReturn(Optional.empty());
        Events dummy = new Events();
        assertThrows(RuntimeException.class, () -> eventService.updateEvent(99, dummy));
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
