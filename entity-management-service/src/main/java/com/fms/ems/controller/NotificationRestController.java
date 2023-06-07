package com.fms.ems.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fms.common.entity.Notification;
import com.fms.common.enums.NotificationType;
import com.fms.ems.services.NotificationService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/nms")
@CrossOrigin(origins = "*")
@Slf4j
public class NotificationRestController {

	@Autowired
	NotificationService notificationService;

	@GetMapping("/notification/{userId}")
	public ResponseEntity<List<Notification>> getAllNotificationsByUserId(@PathVariable String userId) {
		try {
			Set<NotificationType> notificationTypes = new HashSet<>();
			notificationTypes.add(NotificationType.APP);
			notificationTypes.add(NotificationType.ALL);
			final List<Notification> notifications = notificationService.getAllNotificationsByUserId(userId);
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
	public ResponseEntity<Notification> setNotificationAsSeen(@PathVariable String notificationUuid) {
		try {
			final Notification notification = notificationService.setNotificationAsSeen(notificationUuid);
			log.info("Notification reveived: {}", notification);
			if (notification != null) {
				return new ResponseEntity<>(notification, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			System.out.println("Some Exception Occurred: " + e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
