package com.fms.common.events;

import java.util.UUID;

import com.fms.common.enums.PaymentStatus;

import lombok.Data;

@Data
public class PaymentUpdatedBody {

	private UUID paymentUuid;

	private String transactionId;

	private PaymentStatus status;
}
