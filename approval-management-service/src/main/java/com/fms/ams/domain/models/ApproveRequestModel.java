package com.fms.ams.domain.models;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fms.ams.infrastructure.enums.StatusType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApproveRequestModel {

	private UUID approvalUuid;

	private int requestUuid;

	private int approverLevelUuid;

	private int approverUuid;

	private String comments;

	private StatusType statusType;
}
