package com.fms.rms.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString(callSuper = true)
public class RMSEvent {

	Header header;

	String body;

	ErrorList errors;
}
