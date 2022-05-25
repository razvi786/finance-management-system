package com.fms.ams.models;

import lombok.Data;

@Data
public class Error {

	private String errorCode;

	private String errorMessage;

	private String errorDetails;
}
