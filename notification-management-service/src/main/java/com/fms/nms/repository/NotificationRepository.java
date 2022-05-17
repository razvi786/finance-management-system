package com.fms.nms.repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fms.nms.entity.Notification;
import com.fms.nms.enums.NotificationType;

@Repository
public interface NotificationRepository extends MongoRepository<Notification, UUID> {

  List<Notification> findAllByTriggeredToAndNotificationTypeIn(
      int userId, Set<NotificationType> notificationTypes);
}
