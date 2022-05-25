package com.fms.rms.domain.commands;

import com.fms.rms.domain.models.CreateRequestModel;
import com.fms.rms.models.ErrorList;
import com.fms.rms.models.Header;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class InitiateRequestCommand extends BaseCommand {

	private CreateRequestModel body;

	@Builder
	public InitiateRequestCommand(final Header header, final CreateRequestModel body, final ErrorList errors) {
		super(header, errors);
		this.body = body;
	}
}
