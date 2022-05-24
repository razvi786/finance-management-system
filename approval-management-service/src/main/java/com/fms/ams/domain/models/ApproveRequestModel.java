package com.fms.ams.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fms.ams.infrastructure.enums.StatusType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApproveRequestModel {

	private int requiredNumberOfApprovalLevels;

	private int requestUuid;

	private int approverLevelUuid;

	private int approverUuid;

	private String comments;

	private StatusType statusType;
}
