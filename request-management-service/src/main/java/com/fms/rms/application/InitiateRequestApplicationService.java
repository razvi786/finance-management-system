package com.fms.rms.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
	private ObjectMapper mapper;

	@Autowired
	private InitiateRequestDomainService initiateRequestDomainService;

	private static final String EVENT_NAME = RMSConstants.REQUEST_INITIATED;

	@Override
	public String getServiceIdentifier() {
		return EVENT_NAME;
	}

	@Override
	public void process(BaseEvent event) throws JsonProcessingException {

		log.info("Inside InitiateRequestApplicationService.process() method with Event: {}", event);

		CreateRequest createRequestCommandBody = mapper.treeToValue(event.getBody(), CreateRequest.class);

		final InitiateRequestCommand initiateRequestCommand = InitiateRequestCommand.builder().header(event.getHeader())
				.body(createRequestCommandBody).errors(event.getErrors()).build();
		log.info("Initiate Request Command created: {}", initiateRequestCommand);

		initiateRequestDomainService.on(initiateRequestCommand);
	}

}
