package com.fms.pms.events;

import java.util.UUID;

import com.fms.pms.enums.Status;

import lombok.Data;

@Data
public class UpdatePaymentBody {

  private UUID paymentUuid;

  private String transactionId;

  private Status status;
}
