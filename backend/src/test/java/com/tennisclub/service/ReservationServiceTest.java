// src/test/java/com/tennisclub/service/ReservationServiceTest.java
package com.tennisclub.service;

import com.tennisclub.dto.CourtReservationDTO;
import com.tennisclub.model.CourtReservation;
import com.tennisclub.model.User;
import com.tennisclub.repository.CourtReservationRepository;
import com.tennisclub.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

  @Mock
  private CourtReservationRepository reservationRepository;

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private ReservationService reservationService;

  private User testUser;
  private CourtReservationDTO validDTO;

  @BeforeEach
  void setUp() throws Exception {
    testUser = new User();
    testUser.setUserId(1);
    testUser.setEmail("test@example.com");
    testUser.setUsername("testUser");
    when(userRepository.findByEmail("test@example.com")).thenReturn(testUser);

    // Prepare a valid DTO for reservation on a given date and time.
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    validDTO = new CourtReservationDTO("Test User", "test@example.com", 1, "2025-04-10", "08:00");
  }

  @Test
  void createReservation_success() throws Exception {
    // When no overlapping reservations exist.
    when(reservationRepository.findByReservationDateAndCourtNumber(any(Date.class), eq(1)))
      .thenReturn(Collections.emptyList());
    CourtReservation fakeReservation = new CourtReservation();
    fakeReservation.setReservationDate(new Date());
    fakeReservation.setCourtNumber(1);
    when(reservationRepository.save(any(CourtReservation.class))).thenReturn(fakeReservation);

    CourtReservation result = reservationService.createReservation(validDTO);
    assertNotNull(result);
    assertEquals(1, result.getCourtNumber());
  }

  @Test
  void createReservation_overlapping_shouldThrowException() throws Exception {
    // Simulate an existing overlapping reservation.
    CourtReservation existing = new CourtReservation();
    existing.setStartTime(java.sql.Time.valueOf("08:00:00"));
    existing.setEndTime(java.sql.Time.valueOf("09:30:00"));
    when(reservationRepository.findByReservationDateAndCourtNumber(any(Date.class), eq(1)))
      .thenReturn(Collections.singletonList(existing));

    Exception ex = assertThrows(RuntimeException.class,
      () -> reservationService.createReservation(validDTO));
    assertTrue(ex.getMessage().contains("Court 1 is already reserved during the requested time."));
  }
}

