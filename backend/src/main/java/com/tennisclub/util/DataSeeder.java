package com.tennisclub.util;

import com.tennisclub.model.*;
import com.tennisclub.model.enums.BillingPlan;
import com.tennisclub.model.enums.Role;
import com.tennisclub.model.enums.TransactionStatus;
import com.tennisclub.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Seeds the in-memory database with sample data for demonstration:
 * - Admin and regular users
 * - Courts availability
 * - Events
 * - Court reservations
 * - Guest passes
 * - Financial transactions
 */
@Component
public class DataSeeder implements CommandLineRunner {

  private static final Logger logger = LoggerFactory.getLogger(DataSeeder.class);

  private final UserRepository userRepository;
  private final EventRepository eventRepository;
  private final CourtRepository courtRepository;
  private final CourtReservationRepository reservationRepository;
  private final GuestPassRepository guestPassRepository;
  private final FinancialTransactionRepository transactionRepository;
  private final PasswordEncoder passwordEncoder;

  public DataSeeder(
    UserRepository userRepository,
    EventRepository eventRepository,
    CourtRepository courtRepository,
    CourtReservationRepository reservationRepository,
    GuestPassRepository guestPassRepository,
    FinancialTransactionRepository transactionRepository,
    PasswordEncoder passwordEncoder
  ) {
    this.userRepository = userRepository;
    this.eventRepository = eventRepository;
    this.courtRepository = courtRepository;
    this.reservationRepository = reservationRepository;
    this.guestPassRepository = guestPassRepository;
    this.transactionRepository = transactionRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public void run(String... args) {
    seedUsers();
    seedCourts();
    seedEvents();
    seedReservations();
    seedGuestPasses();
    seedTransactions();
  }

  private void seedUsers() {
    if (userRepository.count() > 0) {
      logger.info("Users already seeded. Skipping.");
      return;
    }

    // Admin
    User admin = new User();
    admin.setUsername("admin");
    admin.setFullName("Admin Johnson");
    admin.setEmail("admin@tennis.org");
    admin.setPhoneNumber("202-555-0001");
    admin.setAddress("1 Clubhouse Lane");
    admin.setRole(Role.ADMIN);
    admin.setStatus("Active");
    admin.setBillingPlan(BillingPlan.MONTHLY);
    admin.setPasswordHash(passwordEncoder.encode("pass"));

    // Treasurer
    User treasurer = new User();
    treasurer.setUsername("treasurer");
    treasurer.setFullName("Tina Treasurer");
    treasurer.setEmail("treasury@tennis.org");
    treasurer.setPhoneNumber("222-555-0002");
    treasurer.setAddress("2 Finance Road");
    treasurer.setRole(Role.TREASURER);
    treasurer.setStatus("Active");
    treasurer.setBillingPlan(BillingPlan.ANNUAL);
    treasurer.setPasswordHash(passwordEncoder.encode("treasPass"));

    // Members
    User alice = new User();
    alice.setUsername("alice");
    alice.setFullName("Alice Ace");
    alice.setEmail("alice@tennis.org");
    alice.setPhoneNumber("567-555-0003");
    alice.setAddress("3 Racquet Ave");
    alice.setRole(Role.MEMBER);
    alice.setStatus("Active");
    alice.setBillingPlan(BillingPlan.MONTHLY);
    alice.setPasswordHash(passwordEncoder.encode("alicePass"));

    User bob = new User();
    bob.setUsername("bob");
    bob.setFullName("Bob Backhand");
    bob.setEmail("bob@tennis.org");
    bob.setPhoneNumber("111-555-0004");
    bob.setAddress("4 Serve Street");
    bob.setRole(Role.MEMBER);
    bob.setStatus("Active");
    bob.setBillingPlan(BillingPlan.ANNUAL);
    bob.setPasswordHash(passwordEncoder.encode("bobPass"));

    userRepository.saveAll(Arrays.asList(admin, treasurer, alice, bob));
    logger.info("Seeded users: admin, treasurer, alice, bob.");
  }

  private void seedCourts() {
    if (courtRepository.count() > 0) {
      logger.info("Courts already seeded. Skipping.");
      return;
    }
    for (int i = 1; i <= 6; i++) {
      Court c = new Court();
      c.setCourtNumber(i);
      c.setAvailable(true);
      courtRepository.save(c);
    }
    logger.info("Seeded 6 courts.");
  }

  private void seedEvents() {
    if (eventRepository.count() > 0) {
      logger.info("Events already seeded. Skipping.");
      return;
    }
    List<Events> events = Arrays.asList(
      createEvent("Grand Opening", "Celebrate our new courts!", 2025,5,1, 10,0, "Main Complex"),
      createEvent("Free Play Day", "All-day free court use.", 2025,5,7, 8,0, "All Courts"),
      createEvent("Weekend Mixer", "Join fellow members for fun matches.", 2025,5,14,14,0, "Courts 5 & 6")
    );
    eventRepository.saveAll(events);
    logger.info("Seeded 3 events.");
  }

  private Events createEvent(
    String title, String desc,
    int year,int month,int day,
    int hour,int minute,
    String loc
  ) {
    Events e = new Events();
    e.setTitle(title);
    e.setDescription(desc);
    e.setEventDate(Date.from(
      LocalDate.of(year, month, day)
        .atStartOfDay(ZoneId.systemDefault())
        .toInstant()));
    e.setEventTime(Time.valueOf(LocalTime.of(hour, minute)));
    e.setLocation(loc);
    return e;
  }

  private void seedReservations() {
    if (reservationRepository.count() > 0) {
      logger.info("Reservations already seeded. Skipping.");
      return;
    }
    // Alice books Court 1 on 2025-05-01, 10:00-11:00
    CourtReservation r = new CourtReservation();
    r.setBookedBy(userRepository.findByUsername("alice"));
    r.setCourtNumber(1);
    r.setReservationDate(Date.from(
      LocalDate.of(2025,5,1)
        .atStartOfDay(ZoneId.systemDefault())
        .toInstant()));
    r.setStartTime(Time.valueOf(LocalTime.of(10,0)));
    r.setEndTime(Time.valueOf(LocalTime.of(11,0)));
    reservationRepository.save(r);
    logger.info("Seeded 1 sample reservation for Alice.");
  }

  private void seedGuestPasses() {
    if (guestPassRepository.count() > 0) {
      logger.info("Guest passes already seeded. Skipping.");
      return;
    }
    // Give Bob 2 guest passes this month
    User bob = userRepository.findByUsername("bob");
    for (int i=0; i<2; i++) {
      GuestPass gp = new GuestPass();
      gp.setUser(bob);
      gp.setPrice(Double.parseDouble("15.00"));
      gp.setPurchaseDate(new Date());
      gp.setExpirationDate(Date.from(
        LocalDate.now().plusMonths(1)
          .atStartOfDay(ZoneId.systemDefault())
          .toInstant()));
      gp.setUsed(false);
      guestPassRepository.save(gp);
    }
    logger.info("Seeded 2 guest passes for Bob.");
  }

  private void seedTransactions() {
    if (transactionRepository.count() > 0) {
      logger.info("Transactions already seeded. Skipping.");
      return;
    }
    // Charge monthly fee for Alice
    User alice = userRepository.findByUsername("alice");
    FinancialTransaction t1 = new FinancialTransaction();
    t1.setUser(alice);
    t1.setAmount(new BigDecimal("50.00"));
    t1.setFeeType("membership");
    t1.setStatus(TransactionStatus.PENDING);
    t1.setDescription("Monthly membership fee");
    t1.setTransactionDate(new Date());

    // Charge annual fee for Bob
    User bob = userRepository.findByUsername("bob");
    FinancialTransaction t2 = new FinancialTransaction();
    t2.setUser(bob);
    t2.setAmount(new BigDecimal("400.00"));
    t2.setFeeType("membership");
    t2.setStatus(TransactionStatus.PENDING);
    t2.setDescription("Annual membership fee");
    t2.setTransactionDate(new Date());

    transactionRepository.saveAll(Arrays.asList(t1,t2));
    logger.info("Seeded 2 sample transactions.");
  }
}
