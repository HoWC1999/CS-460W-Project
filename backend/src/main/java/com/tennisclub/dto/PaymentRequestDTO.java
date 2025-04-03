package com.tennisclub.dto;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class PaymentRequestDTO {
  private int userId;
  private BigDecimal amount;
  // Do NOT store raw card data! In a production system, card details should be tokenized.
  // For testing, you might accept dummy card info here, but in production you would use Stripe Elements or Checkout.
  private String cardNumber;
  private String expiry;
  private String cvv;
}
