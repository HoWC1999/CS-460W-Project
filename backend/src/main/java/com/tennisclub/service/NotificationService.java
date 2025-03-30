package com.tennisclub.service;

import com.tennisclub.model.User;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

  // Stub method for sending an email
  public boolean sendEmail(String recipient, String subject, String message) {
    // Integrate with an actual email service (e.g., JavaMailSender)
    System.out.println("Sending email to " + recipient + " with subject: " + subject);
    System.out.println("Message: " + message);
    return true;
  }

  // Stub method for sending a notification (e.g., push or in-app)
  public boolean sendNotification(User user, String message) {
    // Implement notification logic as needed
    System.out.println("Notifying user " + user.getUsername() + ": " + message);
    return true;
  }
}
