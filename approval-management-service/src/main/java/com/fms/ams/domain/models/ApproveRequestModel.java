package com.fms.ams.domain.models;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fms.common.enums.ApprovalStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApproveRequestModel {

	private int requiredNumberOfApprovalLevels;

	private UUID requestUuid;

	private int approverLevelId;

	private int approverId;

	private String comments;

	private ApprovalStatus status;
}
