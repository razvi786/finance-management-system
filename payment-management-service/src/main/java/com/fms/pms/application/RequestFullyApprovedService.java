package com.fms.pms.application;

import static com.fms.pms.constants.PmsConstants.REQUEST_FULLY_APPROVED;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fms.common.BaseEvent;
import com.fms.common.IApplicationService;
import com.fms.common.events.RequestFullyApprovedBody;
import com.fms.pms.domain.commands.HandleRequestFullyApprovedCommand;
import com.fms.pms.domain.services.RequestFullyApprovedDomainService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RequestFullyApprovedService implements IApplicationService {

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private RequestFullyApprovedDomainService requestFullyApprovedDomainService;

	private static final String EVENT_NAME = REQUEST_FULLY_APPROVED;

	@Override
	public String getServiceIdentifier() {
		return EVENT_NAME;
	}

	@Override
	public void process(BaseEvent event) throws JsonProcessingException {

		log.info("Inside RequestFullyApprovedService.process() method with Event: {}", event);

		RequestFullyApprovedBody requestFullyApprovedCommandBody = mapper.treeToValue(event.getBody(),
				RequestFullyApprovedBody.class);

		final HandleRequestFullyApprovedCommand handleRequestFullyApprovedCommand = HandleRequestFullyApprovedCommand
				.builder().header(event.getHeader()).body(requestFullyApprovedCommandBody).errors(event.getErrors())
				.build();
		log.info("HandleRequestFullyApprovedCommand created: {}", handleRequestFullyApprovedCommand);

		requestFullyApprovedDomainService.on(handleRequestFullyApprovedCommand);

	}
}
