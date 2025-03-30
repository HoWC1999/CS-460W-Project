package com.tennisclub.service;

import com.tennisclub.model.User;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

  public boolean sendEmail(String recipient, String subject, String message) {
    // Stub implementation for sending email
    System.out.println("Sending email to " + recipient + ": " + subject);
    return true;
  }

  public boolean sendNotification(User user, String message) {
    // Stub implementation for notifications
    System.out.println("Notifying " + user.getUsername() + ": " + message);
    return true;
  }
}
