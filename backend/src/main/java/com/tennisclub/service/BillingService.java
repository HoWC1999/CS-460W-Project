package com.tennisclub.service;

import com.tennisclub.model.BillingTransaction;
import com.tennisclub.model.User;
import com.tennisclub.repository.BillingTransactionRepository;
import com.tennisclub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class BillingService {

  @Autowired
  private BillingTransactionRepository billingTransactionRepository;

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


  // Get billing history for a user
  public List<BillingTransaction> getBillingHistory(int userId) {
    return billingTransactionRepository.findByUser_UserId(userId);
  }

  public List<BillingTransaction> getAllBilling() {
    return billingTransactionRepository.findAll();
  }
}
