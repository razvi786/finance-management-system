package com.fms.rms.domain.commands;

import com.fms.common.ErrorList;
import com.fms.common.Header;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaseCommand {

	private Header header;

	private ErrorList errors;
}
