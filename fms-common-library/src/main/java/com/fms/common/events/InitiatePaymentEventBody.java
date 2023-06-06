package com.fms.common.events;

import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InitiatePaymentEventBody {

	private UUID requestUuid;

	private int projectId;

	private int vendorId;

	private double amount;

}
