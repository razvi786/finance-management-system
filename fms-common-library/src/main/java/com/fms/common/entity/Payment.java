package com.fms.common.entity;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.fms.common.enums.PaymentStatus;

import lombok.Data;

@Data
public class Payment {

	private UUID paymentUuid;

	private UUID requestUuid;

	private int projectId;

	private String projectName;

	private int vendorId;

	private String vendorName;

	private int userId;

	private String userName;

	private double amount;

	private PaymentStatus status;

	private String transactionId;

	private OffsetDateTime createdDatetime;

	private OffsetDateTime updatedDatetime;

	private Integer concurrencyVersion;
}
