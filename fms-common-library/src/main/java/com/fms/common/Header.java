package com.fms.common;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Header {

	private String eventName;

	private LocalDateTime eventDateTime;

	private String eventFrom;

	private String eventTo;
}
