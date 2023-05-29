package com.fms.common.entity;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.fms.common.enums.StatusType;

import lombok.Data;

@Data
public class Request {

	private UUID requestUuid;

	private int raisedBy;

	private String raisedByName;

	private StatusType status;

	private OffsetDateTime deadlineDatetime;

	private double amount;

	private String description;

	private int projectId;

	private String projectName;

	private OffsetDateTime createdDatetime;

	private OffsetDateTime updatedDatetime;

	private Integer concurrencyVersion;

}
