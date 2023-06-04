package com.fms.common.entity;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.fms.common.enums.ApprovalStatus;

import lombok.Data;

@Data
public class Approval {

	private UUID approvalUuid;

	private UUID requestUuid;

	private int approverLevelId;

	// Extra field
	private int approverLevel;

	private int approverId;

	// Extra field
	private String approverName;

	private String comments;

	private ApprovalStatus status;

	private OffsetDateTime createdDatetime;

	private OffsetDateTime updatedDatetime;

	private Integer concurrencyVersion;
}
