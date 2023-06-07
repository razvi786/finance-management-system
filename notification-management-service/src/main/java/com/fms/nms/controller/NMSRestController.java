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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fms.common.enums.NotificationType;
import com.fms.nms.entity.Notification;
import com.fms.nms.repository.NotificationRepository;
import com.fms.nms.service.EmailSenderService;
import com.fms.nms.service.SmsSenderService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/nms")
@CrossOrigin(origins = "*")
@Slf4j
public class NMSRestController {

	@Autowired
	private NotificationRepository notificationRepository;

	@Autowired
	private EmailSenderService emailSenderService;

	@Autowired
	private SmsSenderService smsSenderService;

	@GetMapping("/notification/{userId}")
	public ResponseEntity<List<Notification>> getAllNotificationsByUserId(@PathVariable String userId) {
		try {
			Set<NotificationType> notificationTypes = new HashSet<>();
			notificationTypes.add(NotificationType.APP);
			notificationTypes.add(NotificationType.EMAIL);
			notificationTypes.add(NotificationType.SMS);
			notificationTypes.add(NotificationType.ALL);
			final List<Notification> notifications = notificationRepository
					.findAllByTriggeredToAndNotificationTypeIn(Integer.parseInt(userId), notificationTypes);
			if (notifications.isEmpty()) {
				return new ResponseEntity<>(notifications, HttpStatus.OK);
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
			final Optional<Notification> notificationOptional = notificationRepository
					.findById(UUID.fromString(notificationUuid));
			if (notificationOptional.isPresent()) {
				final Notification notification = notificationOptional.get();
				notification.setSeenDatetime(OffsetDateTime.now());
				log.info("Updating Notification: {}", notification);
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
	public ResponseEntity<Notification> sendNotificationToUser(@RequestBody Notification notification,
			@PathVariable String userId, @PathVariable String email, @PathVariable String phone) {
		try {
			notification.setNotificationUuid(UUID.randomUUID());
			notification.setNotificationType(NotificationType.ALL);
			notification.setAppSentDatetime(OffsetDateTime.now());

			// Send Email
			final String subject = "Notification from Payment Management Service";
			final boolean isEmailSent = emailSenderService.sendEmail(email, subject, notification.getMessage());
			if (isEmailSent) {
				notification.setEmailSentDatetime(OffsetDateTime.now());
			}

			// Send SMS
			final boolean isSmsSent = smsSenderService.sendSms(phone, notification.getMessage());
			if (isSmsSent) {
				notification.setSmsSentDatetime(OffsetDateTime.now());
			}
			final Notification savedNotification = notificationRepository.save(notification);
			return new ResponseEntity<>(savedNotification, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("Some Exception Occurred: " + e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/notification/send-app/{userId}")
	@Transactional
	public ResponseEntity<Notification> sendAppNotificationToUser(@RequestBody Notification notification,
			@PathVariable String userId) {
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
	public ResponseEntity<Notification> sendEmailNotificationToUser(@RequestBody Notification notification,
			@PathVariable String email) {
		try {

			// Send Email
			final String subject = "Notification from Payment Management Service";
			final boolean isEmailSent = emailSenderService.sendEmail(email, subject, notification.getMessage());
			if (isEmailSent) {
				notification.setNotificationUuid(UUID.randomUUID());
				notification.setNotificationType(NotificationType.EMAIL);
				notification.setEmailSentDatetime(OffsetDateTime.now());
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

	@PostMapping("/notification/send-sms/{phone}")
	@Transactional
	public ResponseEntity<Notification> sendSmsNotificationToUser(@RequestBody Notification notification,
			@PathVariable String phone) {
		try {

			// Send SMS
			final boolean isSmsSent = smsSenderService.sendSms(phone, notification.getMessage());
			if (isSmsSent) {
				notification.setNotificationUuid(UUID.randomUUID());
				notification.setNotificationType(NotificationType.SMS);
				notification.setSmsSentDatetime(OffsetDateTime.now());
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
}
