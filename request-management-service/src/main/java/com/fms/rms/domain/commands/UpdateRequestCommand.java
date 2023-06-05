package com.fms.rms.domain.commands;

import com.fms.common.BaseCommand;
import com.fms.common.ErrorList;
import com.fms.common.Header;
import com.fms.common.events.UpdateRequest;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UpdateRequestCommand extends BaseCommand {

	private UpdateRequest body;

	@Builder
	public UpdateRequestCommand(final Header header, final UpdateRequest body, final ErrorList errors) {
		super(header, errors);
		this.body = body;
	}
}
