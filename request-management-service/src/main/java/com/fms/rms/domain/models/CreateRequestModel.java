package com.fms.rms.domain.models;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fms.rms.enums.StatusType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateRequestModel {

	private UUID requestUuid;

	private int raisedBy;

	private StatusType statusType;

	private OffsetDateTime deadlineDatetime;
}
