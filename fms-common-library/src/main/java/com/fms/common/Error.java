package com.fms.common;

import lombok.Data;

@Data
public class Error {

	private String errorCode;

	private String errorMessage;

	private String errorDetails;
}
