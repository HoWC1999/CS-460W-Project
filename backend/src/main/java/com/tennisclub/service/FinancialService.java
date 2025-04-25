package com.tennisclub.service;

import com.tennisclub.model.FinancialReport;
import com.tennisclub.model.FinancialTransaction;
import com.tennisclub.model.User;
import com.tennisclub.model.enums.BillingPlan;
import com.tennisclub.model.enums.TransactionStatus;
import com.tennisclub.repository.FinancialReportRepository;
import com.tennisclub.repository.FinancialTransactionRepository;
import com.tennisclub.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class FinancialService {

  private static final Logger logger = LoggerFactory.getLogger(FinancialService.class);

  private static final BigDecimal MONTHLY_FEE = new BigDecimal("50");
  private static final BigDecimal ANNUAL_FEE = new BigDecimal("400");

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private FinancialReportRepository reportRepository;

  @Autowired
  private FinancialTransactionRepository financialTransactionRepository;

  // Process a payment or billing transaction.
  public FinancialTransaction processTransaction(FinancialTransaction transaction) {
    try {
      if (transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
        logger.error("Invalid transaction amount: {} for user {}", transaction.getAmount(), transaction.getUser().getUserId());
        throw new IllegalArgumentException("Transaction amount must be greater than zero");
      }
      transaction.setStatus(TransactionStatus.PENDING);
      // Simulate processing (e.g., integration with a payment gateway)
      transaction.setStatus(TransactionStatus.SUCCESS);
      transaction.setTransactionDate(new Date());
      FinancialTransaction savedTransaction = financialTransactionRepository.save(transaction);
      logger.info("Processed transaction successfully: transactionId {}", savedTransaction.getTransactionId());
      return savedTransaction;
    } catch (Exception e) {
      logger.error("Error processing transaction for user {}: {}",
        (transaction.getUser() != null ? transaction.getUser().getUserId() : "unknown"), e.getMessage(), e);
      throw new RuntimeException("Error processing transaction", e);
    }
  }

  // Process a refund transaction.
  public FinancialTransaction processRefund(FinancialTransaction transaction) {
    try {
      if (transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
        logger.error("Invalid refund amount: {}.", transaction.getAmount());
        throw new IllegalArgumentException("Refund amount must be greater than zero");
      }
      transaction.setStatus(TransactionStatus.SUCCESS);
      FinancialTransaction savedTransaction = financialTransactionRepository.save(transaction);
      logger.info("Processed refund successfully: transactionId {}", savedTransaction.getTransactionId());
      return savedTransaction;
    } catch (Exception e) {
      logger.error("Error processing refund: {}", e.getMessage(), e);
      throw new RuntimeException("Error processing refund", e);
    }
  }

  // Charge the annual membership fee.
  public FinancialTransaction chargeAnnualMembershipFee(int userId, BigDecimal amount) {
    try {
      User user = userRepository.findById(userId);
      if (user == null) {
        logger.error("User not found for annual membership fee, userId: {}", userId);
        throw new RuntimeException("User not found");
      }
      FinancialTransaction transaction = new FinancialTransaction();
      transaction.setUser(user);
      transaction.setAmount(amount);
      transaction.setFeeType("membership");
      transaction.setBillingDate(new Date());
      transaction.setDescription("Annual membership fee");
      FinancialTransaction savedTransaction = financialTransactionRepository.save(transaction);
      logger.info("Charged annual membership fee for user {}: transactionId {}", userId, savedTransaction.getTransactionId());
      return savedTransaction;
    } catch (Exception e) {
      logger.error("Error charging annual membership fee for user {}: {}", userId, e.getMessage(), e);
      throw new RuntimeException("Error charging annual membership fee", e);
    }
  }

  // Apply a late fee (10% of the base amount).
  public FinancialTransaction applyLateFee(int userId, BigDecimal baseAmount) {
    try {
      BigDecimal lateFee = baseAmount.multiply(new BigDecimal("0.10"));
      User user = userRepository.findById(userId);
      if (user == null) {
        logger.error("User not found for late fee application, userId: {}", userId);
        throw new RuntimeException("User not found");
      }
      FinancialTransaction transaction = new FinancialTransaction();
      transaction.setUser(user);
      transaction.setAmount(lateFee);
      transaction.setTransactionType("late_fee");
      transaction.setStatus(TransactionStatus.SUCCESS);
      transaction.setDescription("Late payment fee");
      transaction.setTransactionDate(new Date());
      FinancialTransaction savedTransaction = financialTransactionRepository.save(transaction);
      logger.info("Applied late fee for user {}: transactionId {}", userId, savedTransaction.getTransactionId());
      return savedTransaction;
    } catch (Exception e) {
      logger.error("Error applying late fee for user {}: {}", userId, e.getMessage(), e);
      throw new RuntimeException("Error applying late fee", e);
    }
  }

  // Mark a billing record as paid.
  public FinancialTransaction payBill(int transactionId) {
    try {
      Optional<FinancialTransaction> optionalTransaction = financialTransactionRepository.findById(transactionId);
      if (optionalTransaction.isEmpty()) {
        logger.error("Billing record not found for transactionId: {}", transactionId);
        throw new RuntimeException("Billing record not found");
      }
      FinancialTransaction transaction = optionalTransaction.get();
      if (transaction.getStatus() == TransactionStatus.SUCCESS) {
        logger.warn("Billing record already marked as paid: transactionId {}", transactionId);
        throw new RuntimeException("This billing record is already marked as paid.");
      }
      transaction.setStatus(TransactionStatus.SUCCESS);
      FinancialTransaction updatedTransaction = financialTransactionRepository.save(transaction);
      logger.info("Bill payment successful: transactionId {}", transactionId);
      return updatedTransaction;
    } catch (Exception e) {
      logger.error("Error processing bill payment for transactionId {}: {}", transactionId, e.getMessage(), e);
      throw new RuntimeException("Error paying bill", e);
    }
  }

  // Generate a CSV report of all transactions.
  public String generateReport() {
    try {
      List<FinancialTransaction> transactions = financialTransactionRepository.findAll();
      StringBuilder report = new StringBuilder();
      report.append("TransactionId,Amount,Date,Type,Status\n");
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      for (FinancialTransaction ft : transactions) {
        report.append(ft.getTransactionId()).append(",")
          .append(ft.getAmount()).append(",")
          .append(sdf.format(ft.getTransactionDate())).append(",")
          .append(ft.getTransactionType()).append(",")
          .append(ft.getStatus()).append("\n");
      }
      FinancialReport financialReport = new FinancialReport();
      financialReport.setReportData(report.toString());
      reportRepository.save(financialReport);
      logger.info("Financial report generated; {} records included.", transactions.size());
      return report.toString();
    } catch (Exception e) {
      logger.error("Error generating financial report: {}", e.getMessage(), e);
      throw new RuntimeException("Error generating financial report", e);
    }
  }

  // Get all billing transactions.
  public List<FinancialTransaction> getAllBilling() {
    try {
      List<FinancialTransaction> transactions = financialTransactionRepository.findAll();
      logger.info("Fetched {} billing records.", transactions.size());
      return transactions;
    } catch (Exception e) {
      logger.error("Error fetching all billing records: {}", e.getMessage(), e);
      throw new RuntimeException("Error fetching billing records", e);
    }
  }

  // Get billing history for a user.
  public List<FinancialTransaction> getBillingHistory(int userId) {
    try {
      List<FinancialTransaction> transactions = financialTransactionRepository.findByUser_UserId(userId);
      logger.info("Fetched {} billing records for user {}.", transactions.size(), userId);
      return transactions;
    } catch (Exception e) {
      logger.error("Error fetching billing history for user {}: {}", userId, e.getMessage(), e);
      throw new RuntimeException("Error fetching billing history for user", e);
    }
  }

  // Process a card payment; simulate the card processing (e.g., with Stripe).
  public FinancialTransaction processCardPayment(int transactionId, String paymentMethodId) {
    try {
      Optional<FinancialTransaction> optionalTransaction = financialTransactionRepository.findById(transactionId);
      if (optionalTransaction.isEmpty()) {
        logger.error("Billing record not found for card payment, transactionId: {}", transactionId);
        throw new RuntimeException("Billing record not found");
      }
      FinancialTransaction transaction = optionalTransaction.get();
      transaction.setStatus(TransactionStatus.SUCCESS);
      transaction.setDescription(transaction.getDescription() + " | Card payment processed with token: " + paymentMethodId);
      FinancialTransaction savedTransaction = financialTransactionRepository.save(transaction);
      logger.info("Card payment processed successfully for transaction {}", transactionId);
      return savedTransaction;
    } catch (Exception e) {
      logger.error("Error processing card payment for transaction {}: {}", transactionId, e.getMessage(), e);
      throw new RuntimeException("Error processing card payment", e);
    }
  }

  // Charge the monthly fee (set as pending until paid).
  public FinancialTransaction chargeMonthlyFee(int userId) {
    try {
      User user = userRepository.findById(userId);
      if (user == null) {
        logger.error("User not found for monthly fee, userId: {}", userId);
        throw new RuntimeException("User not found");
      }
      FinancialTransaction transaction = new FinancialTransaction();
      transaction.setUser(user);
      transaction.setAmount(MONTHLY_FEE);
      transaction.setTransactionType("monthly");
      transaction.setStatus(TransactionStatus.PENDING);
      transaction.setDescription("Monthly membership fee");
      transaction.setTransactionDate(new Date());
      FinancialTransaction savedTransaction = financialTransactionRepository.save(transaction);
      logger.info("Monthly fee charged for user {}: transactionId {}", userId, savedTransaction.getTransactionId());
      return savedTransaction;
    } catch (Exception e) {
      logger.error("Error charging monthly fee for user {}: {}", userId, e.getMessage(), e);
      throw new RuntimeException("Error charging monthly fee", e);
    }
  }

  // Scheduled task: Process monthly charges on the 1st day of every month.
  @Scheduled(cron = "0 0 0 1 * ?")
  public void processMonthlyCharges() {
    List<User> users = userRepository.findAll();
    logger.info("Starting monthly charges for {} users.", users.size());
    for (User user : users) {
      if (user.getBillingPlan() == BillingPlan.MONTHLY) {
        try {
          chargeMonthlyFee(user.getUserId());
        } catch (Exception e) {
          logger.error("Error charging monthly fee for user {}: {}", user.getUserId(), e.getMessage(), e);
        }
      }
    }
  }

  // Scheduled task: Process annual charges on January 1st.
  @Scheduled(cron = "0 0 0 1 1 ?")
  public void processAnnualCharges() {
    List<User> users = userRepository.findAll();
    logger.info("Starting annual charges for {} users.", users.size());
    for (User user : users) {
      if (user.getBillingPlan() == BillingPlan.ANNUAL) {
        try {
          chargeAnnualMembershipFee(user.getUserId(), ANNUAL_FEE);
        } catch (Exception e) {
          logger.error("Error charging annual fee for user {}: {}", user.getUserId(), e.getMessage(), e);
        }
      }
    }
  }

  // Scheduled task: Check and apply late fees for overdue monthly payments. Runs daily at 1 AM.
  @Scheduled(cron = "0 0 1 * * ?")
  public void processLateFees() {
    try {
      List<FinancialTransaction> pendingMonthly = financialTransactionRepository.findByTransactionTypeAndStatus("monthly", TransactionStatus.PENDING);
      logger.info("Found {} pending monthly transactions.", pendingMonthly.size());
      Date now = new Date();
      for (FinancialTransaction transaction : pendingMonthly) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(transaction.getTransactionDate());
        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date dueDatePlusOne = cal.getTime();
        if (now.after(dueDatePlusOne)) {
          try {
            applyLateFee(transaction.getUser().getUserId(), MONTHLY_FEE);
          } catch (Exception e) {
            logger.error("Error applying late fee for user {}: {}", transaction.getUser().getUserId(), e.getMessage(), e);
          }
        }
      }
    } catch (Exception e) {
      logger.error("Error processing late fees: {}", e.getMessage(), e);
    }
  }

  public FinancialTransaction markAsDisputed(int transactionId, String reason) {
    try {
      Optional<FinancialTransaction> optionalTransaction = financialTransactionRepository.findById(transactionId);
      if (optionalTransaction.isEmpty()) {
        logger.error("Billing record not found for dispute, transactionId: {}", transactionId);
        throw new RuntimeException("Billing record not found");
      }
      FinancialTransaction transaction = optionalTransaction.get();
      transaction.setDescription(transaction.getDescription() + " | Disputed: " + reason);
      FinancialTransaction updatedTransaction = financialTransactionRepository.save(transaction);
      logger.info("Transaction {} marked as disputed with reason: {}", transactionId, reason);
      return updatedTransaction;
    } catch (Exception e) {
      logger.error("Error marking transaction {} as disputed: {}", transactionId, e.getMessage(), e);
      throw new RuntimeException("Error marking transaction as disputed", e);
    }
  }
  public List<FinancialTransaction> getDisputedTransactions() {
    try {
      logger.info("Fetching disputed transactions");
      List<FinancialTransaction> disputed = financialTransactionRepository.findByDescriptionContaining("Disputed:");
      logger.info("Fetched {} disputed transactions", disputed.size());
      return disputed;
    } catch (Exception e) {
      logger.error("Error fetching disputed transactions: {}", e.getMessage(), e);
      throw new RuntimeException("Error fetching disputed transactions: " + e.getMessage());
    }
  }

  public FinancialTransaction markAsRefundIssued(int transactionId) {
    Optional<FinancialTransaction> optionalTransaction = financialTransactionRepository.findById(transactionId);
    if (optionalTransaction.isEmpty()) {
      logger.error("Billing record {} not found for refund.", transactionId);
      throw new RuntimeException("Billing record not found");
    }
    FinancialTransaction transaction = optionalTransaction.get();
    logger.info("Marking transaction {} as refunded.", transactionId);
    // Replace the description with a message that clearly indicates a refund has been issued.
    transaction.setDescription("Refund has been issued");
    // Optionally, update the status to reflect that it is no longer refundable.
    // (If you have a separate REFUNDED enum constant, use that instead.)
    transaction.setStatus(TransactionStatus.SUCCESS);
    try {
      FinancialTransaction savedTransaction = financialTransactionRepository.save(transaction);
      logger.info("Refund processed successfully for transaction {}.", transactionId);
      return savedTransaction;
    } catch (Exception e) {
      logger.error("Error saving refund update for transaction {}: {}", transactionId, e.getMessage(), e);
      throw new RuntimeException("Error processing refund: " + e.getMessage());
    }
  }
}
