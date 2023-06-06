package com.fms.ams.domain.commands;

import com.fms.ams.domain.models.ApproveRequestModel;
import com.fms.common.BaseCommand;
import com.fms.common.ErrorList;
import com.fms.common.Header;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ApproveRequestCommand extends BaseCommand {

	private ApproveRequestModel body;

	@Builder
	public ApproveRequestCommand(final Header header, final ApproveRequestModel body, final ErrorList errors) {
		super(header, errors);
		this.body = body;
	}
}
