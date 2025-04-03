package com.tennisclub.service;

import com.tennisclub.dto.PaymentRequestDTO;
import com.tennisclub.dto.PaymentResponseDTO;
import com.tennisclub.model.BillingTransaction;
import com.tennisclub.model.FinancialTransaction;
import com.tennisclub.model.User;
import com.tennisclub.model.enums.TransactionStatus;
import com.tennisclub.repository.BillingTransactionRepository;
import com.tennisclub.repository.FinancialTransactionRepository;
import com.tennisclub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

@Service
public class BillingService {

  @Autowired
  private BillingTransactionRepository billingTransactionRepository;

  @Autowired
  private FinancialTransactionRepository FinancialTransactionRepository;

  @Autowired
  private UserRepository userRepository;

  // Charge an annual membership fee (for example, $400)
  public BillingTransaction chargeAnnualMembershipFee(int userId, BigDecimal amount) {
    User user = userRepository.findById(userId);
    if (user == null) {
      throw new RuntimeException("User not found");
    }
    BillingTransaction billing = new BillingTransaction();
    billing.setUser(user);
    billing.setAmount(amount);
    billing.setFeeType("membership");
    billing.setBillingDate(new Date());
    billing.setDescription("Annual membership fee");
    return billingTransactionRepository.save(billing);
  }

  // Apply a late fee (e.g., 10% of the base amount)
  public BillingTransaction applyLateFee(int userId, BigDecimal baseAmount) {
    User user = userRepository.findById(userId);
    if (user == null) {
      throw new RuntimeException("User not found");
    }
    BigDecimal lateFee = baseAmount.multiply(new BigDecimal("0.10"));
    BillingTransaction billing = new BillingTransaction();
    billing.setUser(user);
    billing.setAmount(lateFee);
    billing.setFeeType("late_fee");
    billing.setBillingDate(new Date());
    billing.setDescription("Late payment fee");
    return billingTransactionRepository.save(billing);
  }
  @Autowired
  private PaymentProcessorService paymentProcessorService;

  public FinancialTransaction processCardPayment(int userId, BigDecimal amount, PaymentRequestDTO paymentRequest) {
    PaymentResponseDTO response = paymentProcessorService.processPayment(paymentRequest);
    if (!response.isSuccess()) {
      throw new RuntimeException("Card payment failed: " + response.getMessage());
    }
    // Create a financial transaction record, similar to previous examples
    FinancialTransaction transaction = new FinancialTransaction();
    User user = userRepository.findById(userId);
    if (user == null) {
      throw new RuntimeException("User not found");
    }
    transaction.setUser(user);
    transaction.setAmount(amount);
    transaction.setTransactionType("CARD_PAYMENT");
    transaction.setStatus(TransactionStatus.SUCCESS);
    transaction.setDescription("Card payment processed, transaction id: " + response.getTransactionId());
    transaction.setTransactionDate(new Date());
    return FinancialTransactionRepository.save(transaction);
  }
  public FinancialTransaction markAsDisputed(int billingId, String reason) {
    Optional<FinancialTransaction> optionalTransaction = FinancialTransactionRepository.findById(billingId);
    if (optionalTransaction.isEmpty()) {
      throw new RuntimeException("Billing record not found");
    }
    FinancialTransaction transaction = optionalTransaction.get();
    // For example, you might set a dispute flag or update the description.
    // Here, we'll append the dispute reason and mark it as disputed.
    transaction.setDescription(transaction.getDescription() + " | Disputed: " + reason);
    // Optionally, if you have a separate boolean flag:
    // transaction.setDisputed(true);
    return FinancialTransactionRepository.save(transaction);
  }


}
