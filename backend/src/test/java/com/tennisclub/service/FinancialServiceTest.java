package com.tennisclub.service;

import com.tennisclub.model.FinancialTransaction;
import com.tennisclub.model.User;
import com.tennisclub.model.enums.BillingPlan;
import com.tennisclub.model.enums.TransactionStatus;
import com.tennisclub.repository.FinancialTransactionRepository;
import com.tennisclub.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FinancialServiceTest {

  @InjectMocks
  private FinancialService financialService;

  @Mock
  private UserRepository userRepository;

  @Mock
  private FinancialTransactionRepository financialTransactionRepository;

  private User testUser;

  @BeforeEach
  public void init() {
    MockitoAnnotations.openMocks(this);
    testUser = new User("finUser", "pass", "fin@example.com", null, "Active");
    testUser.setUserId(2);
    testUser.setBillingPlan(BillingPlan.MONTHLY);
    when(userRepository.findByUserId(2)).thenReturn(testUser);
  }

  @Test
  public void testChargeAnnualMembershipFee() {
    BigDecimal fee = new BigDecimal("400");
    FinancialTransaction dummyTx = new FinancialTransaction();
    dummyTx.setTransactionId(1);
    when(financialTransactionRepository.save(any(FinancialTransaction.class))).thenAnswer(i -> i.getArgument(0));

    FinancialTransaction tx = financialService.chargeAnnualMembershipFee(2, fee);
    assertEquals(testUser, tx.getUser());
    assertEquals(fee, tx.getAmount());
    assertEquals("membership", tx.getFeeType());
  }

  @Test
  public void testApplyLateFee() {
    BigDecimal baseAmount = new BigDecimal("50");
    FinancialTransaction dummyTx = new FinancialTransaction();
    dummyTx.setTransactionId(2);
    when(financialTransactionRepository.save(any(FinancialTransaction.class))).thenAnswer(i -> i.getArgument(0));

    FinancialTransaction tx = financialService.applyLateFee(2, baseAmount);
    assertEquals(testUser.getUserId(), tx.getUser().getUserId());
    // Late fee should be 10% of 50 => 5.00
    assertEquals(new BigDecimal("5.00"), tx.getAmount());
  }

  @Test
  public void testProcessCardPayment() {
    FinancialTransaction pendingTx = new FinancialTransaction();
    pendingTx.setTransactionId(3);
    pendingTx.setStatus(TransactionStatus.PENDING);
    pendingTx.setUser(testUser);
    when(financialTransactionRepository.findById(3)).thenReturn(Optional.of(pendingTx));
    when(financialTransactionRepository.save(any(FinancialTransaction.class))).thenAnswer(i -> i.getArgument(0));

    FinancialTransaction tx = financialService.processCardPayment(3, "testToken123");
    assertEquals(TransactionStatus.SUCCESS, tx.getStatus());
    assertTrue(tx.getDescription().contains("Card payment processed with token: testToken123"));
  }
}

