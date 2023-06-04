package com.fms.common.events;

import java.io.Serializable;
import java.util.UUID;

import com.fms.common.enums.ApprovalStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestApprovedEvent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4888518214366318357L;

	private UUID approvalUuid;

	private UUID requestUuid;

	private int approverLevelId;

	private int approverId;

	private String comments;

	private ApprovalStatus status;
}
