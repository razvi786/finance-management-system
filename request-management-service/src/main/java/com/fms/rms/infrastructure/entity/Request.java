package com.fms.rms.infrastructure.entity;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fms.rms.enums.StatusType;

import lombok.Data;

@Data
@Document(collection = "request")
public class Request {

	@Id
	@Field(name = "request_uuid")
	private UUID requestUuid;

	@Field(name = "raised_by")
	private int raisedBy;

	@Field(name = "status_type")
	private StatusType statusType;

	@Field(name = "deadline_datetime")
	private OffsetDateTime deadlineDatetime;

	@Field(name = "created_datetime")
	private OffsetDateTime createdDatetime;

	@Field(name = "updated_datetime")
	private OffsetDateTime updatedDatetime;

	@Version
	@Field(name = "concurrency_version")
	private Integer concurrencyVersion;

}
