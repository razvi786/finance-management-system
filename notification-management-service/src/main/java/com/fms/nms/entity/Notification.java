package com.fms.nms.entity;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fms.common.enums.NotificationType;

import lombok.Data;

@Data
@Document(collection = "notification")
public class Notification {

	@Id
	@Field(name = "notification_uuid")
	private UUID notificationUuid;

	@Field(name = "notification_type")
	private NotificationType notificationType;

	@Field(name = "message")
	private String message;

	@Field(name = "triggered_by")
	private int triggeredBy;

	@Field(name = "triggered_to")
	private int triggeredTo;

	@Field(name = "seen_datetime")
	private OffsetDateTime seenDatetime;

	@Field(name = "app_sent_datetime")
	private OffsetDateTime appSentDatetime;

	@Field(name = "email_sent_datetime")
	private OffsetDateTime emailSentDatetime;

	@Field(name = "sms_sent_datetime")
	private OffsetDateTime smsSentDatetime;

	@Version
	@Field(name = "concurrency_version")
	private Integer concurrencyVersion;
}
