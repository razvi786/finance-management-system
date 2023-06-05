package com.fms.common.events;

import lombok.Data;

@Data
public class RequestFullyApprovedBody {

  private int requestId;

  private int projectId;

  private String projectName;

  private int vendorId;

  private double requestAmount;

  private int projectAssignedTo;
}
