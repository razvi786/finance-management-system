package com.fms.pms.domain.commands;

import com.fms.common.BaseCommand;
import com.fms.common.ErrorList;
import com.fms.common.Header;
import com.fms.common.events.UpdatePaymentBody;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
public class HandleUpdatePaymentCommand extends BaseCommand {

	private UpdatePaymentBody body;

	@Builder
	public HandleUpdatePaymentCommand(final Header header, final UpdatePaymentBody body, final ErrorList errors) {
		super(header, errors);
		this.body = body;
	}
}
