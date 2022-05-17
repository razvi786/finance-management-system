package com.fms.nms.controller;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fms.nms.entity.Notification;
import com.fms.nms.enums.NotificationType;
import com.fms.nms.repository.NotificationRepository;

@RestController
@RequestMapping("/api")
public class NotificationRestController {

  @Autowired NotificationRepository notificationRepository;

  @GetMapping("/notification/{userId}")
  public ResponseEntity<List<Notification>> getAllNotificationsByUserId(
      @PathVariable String userId) {
    try {
      Set<NotificationType> notificationTypes = new HashSet<>();
      notificationTypes.add(NotificationType.APP);
      notificationTypes.add(NotificationType.ALL);
      final List<Notification> notifications =
          notificationRepository.findAllByTriggeredToAndNotificationTypeIn(
              Integer.parseInt(userId), notificationTypes);
      if (notifications.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      } else {
        return new ResponseEntity<>(notifications, HttpStatus.OK);
      }
    } catch (Exception e) {
      System.out.println("Some Exception Occurred: " + e);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/notification/{notificationUuid}/seen")
  @Transactional
  public ResponseEntity<Notification> setNotificationAsSeen(@PathVariable String notificationUuid) {
    try {
      final Optional<Notification> notificationOptional =
          notificationRepository.findById(UUID.fromString(notificationUuid));
      if (notificationOptional.isPresent()) {
        final Notification notification = notificationOptional.get();
        notification.setSeenDatetime(OffsetDateTime.now());
        final Notification savedNotification = notificationRepository.save(notification);
        return new ResponseEntity<>(savedNotification, HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
    } catch (Exception e) {
      System.out.println("Some Exception Occurred: " + e);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping("/notification/send-all/{userId}/{email}/{phone}")
  @Transactional
  public ResponseEntity<Notification> sendNotificationToUser(
      @RequestBody Notification notification,
      @PathVariable String userId,
      @PathVariable String email,
      @PathVariable String phone) {
    try {
      notification.setNotificationUuid(UUID.randomUUID());
      notification.setNotificationType(NotificationType.ALL);
      notification.setAppSentDatetime(OffsetDateTime.now());
      // TODO: Send Email notification
      notification.setEmailSentDatetime(OffsetDateTime.now());
      // TODO: Send SMS notification
      notification.setSmsSentDatetime(OffsetDateTime.now());
      final Notification savedNotification = notificationRepository.save(notification);
      return new ResponseEntity<>(savedNotification, HttpStatus.OK);
    } catch (Exception e) {
      System.out.println("Some Exception Occurred: " + e);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping("/notification/send-app/{userId}")
  @Transactional
  public ResponseEntity<Notification> sendAppNotificationToUser(
      @RequestBody Notification notification, @PathVariable String userId) {
    try {
      notification.setNotificationUuid(UUID.randomUUID());
      notification.setNotificationType(NotificationType.APP);
      notification.setAppSentDatetime(OffsetDateTime.now());
      final Notification savedNotification = notificationRepository.save(notification);
      return new ResponseEntity<>(savedNotification, HttpStatus.OK);
    } catch (Exception e) {
      System.out.println("Some Exception Occurred: " + e);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping("/notification/send-email/{email}")
  @Transactional
  public ResponseEntity<Notification> sendEmailNotificationToUser(
      @RequestBody Notification notification, @PathVariable String email) {
    try {
      notification.setNotificationUuid(UUID.randomUUID());
      notification.setNotificationType(NotificationType.EMAIL);
      // TODO: Send Email notification
      notification.setEmailSentDatetime(OffsetDateTime.now());
      final Notification savedNotification = notificationRepository.save(notification);
      return new ResponseEntity<>(savedNotification, HttpStatus.OK);
    } catch (Exception e) {
      System.out.println("Some Exception Occurred: " + e);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping("/notification/send-sms/{phone}")
  @Transactional
  public ResponseEntity<Notification> sendSmsNotificationToUser(
      @RequestBody Notification notification, @PathVariable String phone) {
    try {
      notification.setNotificationUuid(UUID.randomUUID());
      notification.setNotificationType(NotificationType.SMS);
      // TODO: Send SMS notification
      notification.setSmsSentDatetime(OffsetDateTime.now());
      final Notification savedNotification = notificationRepository.save(notification);
      return new ResponseEntity<>(savedNotification, HttpStatus.OK);
    } catch (Exception e) {
      System.out.println("Some Exception Occurred: " + e);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
