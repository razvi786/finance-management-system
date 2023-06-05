package com.fms.pms.infrastructure.entity;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fms.common.enums.PaymentStatus;

import lombok.Data;

@Data
@Document(collection = "payment")
public class Payment {

	@Id
	@Field(name = "payment_uuid")
	private UUID paymentUuid;

	@Field(name = "request_uuid")
	private UUID requestUuid;

	@Field(name = "project_id")
	private int projectId;

	@Field(name = "vendor_id")
	private int vendorId;

	@Field(name = "user_id")
	private int userId;

	@Field(name = "amount")
	private double amount;

	@Field(name = "status")
	private PaymentStatus status;

	@Field(name = "transaction_id")
	private String transactionId;

	@Field(name = "created_datetime")
	private OffsetDateTime createdDatetime;

	@Field(name = "updated_datetime")
	private OffsetDateTime updatedDatetime;

	@Version
	@Field(name = "concurrency_version")
	private Integer concurrencyVersion;
}
