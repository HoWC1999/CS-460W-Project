package com.tennisclub.util;

import com.tennisclub.model.User;
import com.tennisclub.model.enums.BillingPlan;
import com.tennisclub.model.enums.Role;
import com.tennisclub.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

  private static final Logger logger = LoggerFactory.getLogger(DataSeeder.class);
  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  public DataSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;

  }

  @Override
  public void run(String... args) throws Exception {
    // Check if the admin user already exists based on email
    String adminEmail = "ad@min.org";
    if (userRepository.findByEmail(adminEmail) == null) {
      User admin = new User();
      admin.setAddress("20 Beans Way");
      admin.setBillingPlan(BillingPlan.valueOf("MONTHLY")); // Ensure your User class has this property.
      admin.setEmail(adminEmail);
      admin.setFullName("Admin");
      // Use the already hashed password provided for the admin
      admin.setPasswordHash("$2a$10$Tqv7Lrq47DrEKAJGCUIwEOKnPkc8ur1dcQVkaR6T.j7yioKsFMe2C");
      admin.setPhoneNumber("1111111111");
      admin.setRole(Role.valueOf("ADMIN"));
      admin.setStatus("Active");
      admin.setUsername("admin");

      userRepository.save(admin);
      logger.info("Admin user seeded successfully.");
    } else {
      logger.info("Admin user already exists. Skipping seeding.");
    }
  }
}
