package com.fms.ams.domain.commands;

import com.fms.ams.models.ErrorList;
import com.fms.ams.models.Header;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaseCommand {

	private Header header;

	private ErrorList errors;
}
