package com.fms.common.events;

import java.time.OffsetDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
//@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateRequest {

	private int raisedBy;

	private OffsetDateTime deadlineDatetime;

	private double amount;

	private String description;

	private int projectId;

}
