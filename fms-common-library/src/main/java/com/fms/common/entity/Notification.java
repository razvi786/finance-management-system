package com.fms.common.entity;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.fms.common.enums.NotificationType;

import lombok.Data;

@Data
public class Notification {

	private UUID notificationUuid;

	private NotificationType notificationType;

	private String message;

	private int triggeredBy;

	private int triggeredTo;

	private OffsetDateTime seenDatetime;

	private OffsetDateTime appSentDatetime;

	private OffsetDateTime emailSentDatetime;

	private OffsetDateTime smsSentDatetime;
}
