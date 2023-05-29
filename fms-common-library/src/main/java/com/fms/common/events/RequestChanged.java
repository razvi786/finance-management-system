package com.fms.common.events;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

import com.fms.common.enums.StatusType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestChanged implements Serializable {

	private static final long serialVersionUID = -7387655428678229952L;

	private UUID requestUuid;

	private int raisedBy;

	private StatusType statusType;

	private OffsetDateTime deadlineDatetime;
}
