package com.fms.common.events;

import java.util.UUID;

import lombok.Data;

@Data
public class PaymentInitiatedBody {

  private int requestId;

  private int projectId;

  private String projectName;

  private int vendorId;

  private double requestAmount;

  private int projectAssignedTo;

  private UUID paymentUuid;
}
