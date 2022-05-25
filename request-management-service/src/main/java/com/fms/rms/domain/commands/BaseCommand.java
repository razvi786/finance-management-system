package com.fms.rms.domain.commands;

import com.fms.rms.models.ErrorList;
import com.fms.rms.models.Header;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaseCommand {

	private Header header;

	private ErrorList errors;
}
