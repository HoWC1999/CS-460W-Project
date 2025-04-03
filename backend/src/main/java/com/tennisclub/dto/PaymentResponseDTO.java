package com.tennisclub.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentResponseDTO {
  private boolean success;
  private String transactionId;
  private String message;
}
