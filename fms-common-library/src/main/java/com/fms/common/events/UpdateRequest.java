package com.fms.common.events;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fms.common.enums.RequestStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateRequest {

	private UUID requestUuid;

	private int raisedBy;

	private RequestStatus statusType;

	private OffsetDateTime deadlineDatetime;
}
