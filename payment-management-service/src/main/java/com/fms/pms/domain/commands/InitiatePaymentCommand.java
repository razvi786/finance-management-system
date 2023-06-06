package com.fms.pms.domain.commands;

import com.fms.common.BaseCommand;
import com.fms.common.ErrorList;
import com.fms.common.Header;
import com.fms.common.events.InitiatePaymentEventBody;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
public class InitiatePaymentCommand extends BaseCommand {

	private InitiatePaymentEventBody body;

	@Builder
	public InitiatePaymentCommand(final Header header, final InitiatePaymentEventBody body, final ErrorList errors) {
		super(header, errors);
		this.body = body;
	}
}
