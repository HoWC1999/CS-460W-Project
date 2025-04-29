// src/main/java/com/tennisclub/dto/RegistrationRequest.java
package com.tennisclub.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Payload for signing up a user to an event.
 */
@Getter @Setter
public class RegistrationRequest {
  private int userId;
}
