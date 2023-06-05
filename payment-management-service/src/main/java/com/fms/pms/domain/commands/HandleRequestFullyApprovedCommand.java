package com.fms.pms.domain.commands;

import com.fms.common.BaseCommand;
import com.fms.common.ErrorList;
import com.fms.common.Header;
import com.fms.common.events.RequestFullyApprovedBody;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
public class HandleRequestFullyApprovedCommand extends BaseCommand {

	private RequestFullyApprovedBody body;

	@Builder
	public HandleRequestFullyApprovedCommand(final Header header, final RequestFullyApprovedBody body,
			final ErrorList errors) {
		super(header, errors);
		this.body = body;
	}
}
