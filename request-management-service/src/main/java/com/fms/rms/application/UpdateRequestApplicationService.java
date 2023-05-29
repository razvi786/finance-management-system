package com.fms.rms.application;

import org.springframework.stereotype.Service;

import com.fms.common.BaseEvent;
import com.fms.common.IApplicationService;
import com.fms.common.events.UpdateRequest;
import com.fms.rms.constants.RMSConstants;
import com.fms.rms.domain.commands.UpdateRequestCommand;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UpdateRequestApplicationService implements IApplicationService {

	private static final String EVENT_NAME = RMSConstants.REQUEST_UPDATED;

	@Override
	public String getServiceIdentifier() {
		return EVENT_NAME;
	}

	@Override
	public void process(BaseEvent event) {

		final UpdateRequestCommand updateRequestCommand = UpdateRequestCommand.builder().header(event.getHeader())
				.body((UpdateRequest) event.getBody()).errors(event.getErrors()).build();
		log.debug("Update Request Command created: {}", updateRequestCommand);

//			initiateRequestDomainService.on(initiateRequestCommand);
	}

}
