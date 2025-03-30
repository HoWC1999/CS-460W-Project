package com.tennisclub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class TennisClubApplication {

  // Logger to output startup details
  private static final Logger logger = LoggerFactory.getLogger(TennisClubApplication.class);

  public static void main(String[] args) {
    // Launch the Spring Boot application and capture the application context.
    ApplicationContext context = SpringApplication.run(TennisClubApplication.class, args);

    // Log that the application has started.
    logger.info("Tennis Club Management System has started successfully!");

    // Optionally, list all the beans loaded (useful for debugging)
    String[] beanNames = context.getBeanDefinitionNames();
    logger.debug("Loaded beans in the application context:");
    for (String beanName : beanNames) {
      logger.debug(beanName);
    }
  }
}
