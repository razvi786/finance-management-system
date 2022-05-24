package com.fms.ams.infrastructure.events;

import java.io.Serializable;
import java.util.UUID;

import com.fms.ams.infrastructure.enums.StatusType;

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

	private int requestUuid;

	private int approverLevelUuid;

	private int approverUuid;

	private String comments;

	private StatusType statusType;
}
