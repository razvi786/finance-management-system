package com.fms.ams.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString(callSuper = true)
public class AMSEvent {

	Header header;

	String body;

	ErrorList errors;
}
