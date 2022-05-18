package com.fms.pms.models;

import lombok.Data;

@Data
public class Error {

  private String errorCode;

  private String errorMessage;

  private String errorDetails;
}
