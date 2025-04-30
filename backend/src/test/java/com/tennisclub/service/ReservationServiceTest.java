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

import java.util.Collections;
import java.util.Date;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

  @Mock
  private CourtReservationRepository reservationRepository;

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private ReservationService reservationService;

  private User testUser;
  private CourtReservationDTO validDTO;

  @BeforeEach
  void setUp() {
    testUser = new User();
    testUser.setUserId(1);
    testUser.setEmail("test@example.com");
    testUser.setUsername("testUser");
    when(userRepository.findByEmail("test@example.com")).thenReturn(testUser);

    // Prepare a valid DTO for a future reservation date
    validDTO = new CourtReservationDTO(
      1,
      "2025-05-01",   // moved from 2025-04-10 to a future date
      "08:00"
    );
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
  void createReservation_overlapping_shouldThrowException() {
    // Arrange: simulate an existing overlapping reservation.
    CourtReservation existing = new CourtReservation();
    existing.setStartTime(java.sql.Time.valueOf("08:00:00"));
    existing.setEndTime(java.sql.Time.valueOf("09:30:00"));
    when(reservationRepository.findByReservationDateAndCourtNumber(
      any(Date.class), eq(1)))
      .thenReturn(Collections.singletonList(existing));

    // Act & Assert: expect an exception mentioning a reserve/overlap conflict
    RuntimeException ex = assertThrows(RuntimeException.class,
      () -> reservationService.createReservation(validDTO));

    String msg = ex.getMessage().toLowerCase();
    assertTrue(
      msg.contains("reserved") || msg.contains("overlap"),
      () -> "Expected exception message to mention reservation conflict, but was: \"" + ex.getMessage() + "\""
    );
  }
}
