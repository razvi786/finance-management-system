package com.fms.ams.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class AMSEvent {

	Header header;

	String body;

	ErrorList errors;
}
