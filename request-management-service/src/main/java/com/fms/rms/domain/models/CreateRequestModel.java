package com.fms.rms.domain.models;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateRequestModel {

	private int raisedBy;

	private OffsetDateTime deadlineDatetime;
}
