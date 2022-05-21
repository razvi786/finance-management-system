package com.fms.ams.infrastructure.entity;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fms.ams.infrastructure.enums.StatusType;

import lombok.Data;

@Data
@Document(collection = "approval")
public class Approval {

	@Id
	@Field(name = "approval_uuid")
	private UUID approvalUuid;

	@Field(name = "request_uuid")
	private int requestUuid;

	@Field(name = "approver_level_uuid")
	private int approverLevelUuid;

	@Field(name = "approver_uuid")
	private int approverUuid;

	@Field(name = "comments")
	private String comments;

	@Field(name = "status_type")
	private StatusType statusType;

	@Field(name = "created_datetime")
	private OffsetDateTime createdDatetime;

	@Field(name = "updated_datetime")
	private OffsetDateTime updatedDatetime;

	@Version
	@Field(name = "concurrency_version")
	private Integer concurrencyVersion;
}
