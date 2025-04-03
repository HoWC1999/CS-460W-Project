package com.tennisclub.service;

import com.tennisclub.dto.PaymentRequestDTO;
import com.tennisclub.dto.PaymentResponseDTO;

public interface PaymentProcessorService {
  PaymentResponseDTO processPayment(PaymentRequestDTO request);
}
