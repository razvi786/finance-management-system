package com.fms.rms.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fms.common.BaseEvent;
import com.fms.common.IApplicationService;
import com.fms.common.events.UpdateRequest;
import com.fms.rms.constants.RMSConstants;
import com.fms.rms.domain.commands.UpdateRequestCommand;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UpdateRequestApplicationService implements IApplicationService {

	@Autowired
	private ObjectMapper mapper;

	private static final String EVENT_NAME = RMSConstants.REQUEST_UPDATED;

	@Override
	public String getServiceIdentifier() {
		return EVENT_NAME;
	}

	@Override
	public void process(BaseEvent event) throws JsonProcessingException {

		UpdateRequest updateRequestCommandBody = mapper.treeToValue(event.getBody(), UpdateRequest.class);

		final UpdateRequestCommand updateRequestCommand = UpdateRequestCommand.builder().header(event.getHeader())
				.body(updateRequestCommandBody).errors(event.getErrors()).build();
		log.debug("Update Request Command created: {}", updateRequestCommand);

//			initiateRequestDomainService.on(initiateRequestCommand);
	}

}
