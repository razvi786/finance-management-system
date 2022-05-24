package com.fms.rms.domain.commands;

import com.fms.rms.domain.models.UpdateRequestModel;
import com.fms.rms.models.ErrorList;
import com.fms.rms.models.Header;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UpdateRequestCommand extends BaseCommand {

	private UpdateRequestModel body;

	@Builder
	public UpdateRequestCommand(final Header header, final UpdateRequestModel body, final ErrorList errors) {
		super(header, errors);
		this.body = body;
	}
}
