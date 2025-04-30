// src/main/java/com/tennisclub/dto/CourtReservationDTO.java
package com.tennisclub.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object for Court Reservation requests.
 * Reservations are always tied to the authenticated user,
 * so we no longer collect name/email here.
 */
@Getter
@Setter
@NoArgsConstructor
public class CourtReservationDTO {
  private int userId;
  /**
   * The court number being reserved (e.g., 1-12).
   */
  private int court;

  /**
   * The reservation date in ISO format (yyyy-MM-dd), e.g. "2025-04-01".
   */
  private String date;

  /**
   * The reservation start time in HH:mm format, e.g. "08:00".
   */
  private String time;

  public CourtReservationDTO(int court, String date, String time) {

    this.court = court;
    this.date = date;
    this.time = time;
  }

  @Override
  public String toString() {
    return "CourtReservationDTO{" +
      "courtNumber=" + court +
      ", date='" + date + '\'' +
      ", time='" + time + '\'' +
      '}';
  }
}

