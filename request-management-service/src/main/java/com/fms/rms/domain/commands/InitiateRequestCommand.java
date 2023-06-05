package com.fms.rms.domain.commands;

import com.fms.common.BaseCommand;
import com.fms.common.ErrorList;
import com.fms.common.Header;
import com.fms.common.events.CreateRequest;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class InitiateRequestCommand extends BaseCommand {

	private CreateRequest body;

	@Builder
	public InitiateRequestCommand(final Header header, final CreateRequest body, final ErrorList errors) {
		super(header, errors);
		this.body = body;
	}
}
