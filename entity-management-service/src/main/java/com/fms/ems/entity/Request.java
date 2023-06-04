package com.fms.ems.entity;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;

import com.fms.common.enums.RequestStatus;

import lombok.Data;

@Data
public class Request {

	@Id
	private UUID requestUuid;

	private int raisedBy;

	private RequestStatus status;

	private OffsetDateTime deadlineDatetime;

	private double amount;

	private String description;

	private int projectId;

	private OffsetDateTime createdDatetime;

	private OffsetDateTime updatedDatetime;

	private Integer concurrencyVersion;

}
