package com.fms.rms.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fms.rms.constants.RMSConstants;
import com.fms.rms.domain.commands.InitiateRequestCommand;
import com.fms.rms.domain.models.CreateRequestModel;
import com.fms.rms.domain.services.InitiateRequestDomainService;
import com.fms.rms.models.RMSEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class InitiateRequestApplicationService implements IApplicationService {

	@Autowired
	private InitiateRequestDomainService initiateRequestDomainService;

	private static final String EVENT_NAME = RMSConstants.REQUEST_INITIATE_RESOURCE_TYPE;

	@Override
	public String getServiceIdentifier() {
		return EVENT_NAME;
	}

	@Override
	public void process(RMSEvent rmsEvent) throws JsonProcessingException {
		try {

			final CreateRequestModel requestBody = IApplicationService.getObjectMapper().readValue(rmsEvent.getBody(),
					CreateRequestModel.class);

			final InitiateRequestCommand initiateRequestCommand = InitiateRequestCommand.builder()
					.header(rmsEvent.getHeader()).body(requestBody).errors(rmsEvent.getErrors()).build();
			log.debug("Initiate Request Command created: {}", initiateRequestCommand);

			initiateRequestDomainService.on(initiateRequestCommand);

		} catch (JsonProcessingException exception) {

			log.error("Exception while mapping eventBody to Command: {}", exception);
		}
	}

}
