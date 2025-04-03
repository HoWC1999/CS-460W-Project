package com.tennisclub.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import com.tennisclub.dto.PaymentRequestDTO;
import com.tennisclub.dto.PaymentResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class StripePaymentProcessorService implements PaymentProcessorService {

  // Inject your secret API key from application properties/environment
  public StripePaymentProcessorService(@Value("${stripe.api.key}") String apiKey) {
    Stripe.apiKey = apiKey;
  }

  @Override
  public PaymentResponseDTO processPayment(PaymentRequestDTO request) {
    // Stripe expects amount in the smallest currency unit (e.g., cents for USD)
    long amountInCents = request.getAmount().multiply(new BigDecimal("100")).longValue();

    PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
      .setAmount(amountInCents)
      .setCurrency("usd")
      .setDescription("Charge for user " + request.getUserId())
      .build();
    PaymentResponseDTO response = new PaymentResponseDTO();
    try {
      PaymentIntent intent = PaymentIntent.create(params);
      response.setSuccess(true);
      response.setTransactionId(intent.getId());
      response.setMessage("Payment processed successfully via Stripe.");
    } catch (StripeException e) {
      response.setSuccess(false);
      response.setMessage("Stripe error: " + e.getMessage());
    }
    return response;
  }
}
