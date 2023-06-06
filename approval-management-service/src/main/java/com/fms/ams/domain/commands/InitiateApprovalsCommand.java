package com.fms.ams.domain.commands;

import com.fms.common.BaseCommand;
import com.fms.common.ErrorList;
import com.fms.common.Header;
import com.fms.common.events.InitiateApprovalsEventBody;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class InitiateApprovalsCommand extends BaseCommand {

	private InitiateApprovalsEventBody body;

	@Builder
	public InitiateApprovalsCommand(final Header header, final InitiateApprovalsEventBody body,
			final ErrorList errors) {
		super(header, errors);
		this.body = body;
	}
}
