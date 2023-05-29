package com.fms.rms.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fms.common.BaseEvent;
import com.fms.common.IApplicationService;
import com.fms.common.events.CreateRequest;
import com.fms.rms.constants.RMSConstants;
import com.fms.rms.domain.commands.InitiateRequestCommand;
import com.fms.rms.domain.services.InitiateRequestDomainService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class InitiateRequestApplicationService implements IApplicationService {

	@Autowired
	private InitiateRequestDomainService initiateRequestDomainService;

	private static final String EVENT_NAME = RMSConstants.REQUEST_INITIATED;

	@Override
	public String getServiceIdentifier() {
		return EVENT_NAME;
	}

	@Override
	public void process(BaseEvent event) {

		final InitiateRequestCommand initiateRequestCommand = InitiateRequestCommand.builder().header(event.getHeader())
				.body((CreateRequest) event.getBody()).errors(event.getErrors()).build();
		log.debug("Initiate Request Command created: {}", initiateRequestCommand);

		initiateRequestDomainService.on(initiateRequestCommand);
	}

}
