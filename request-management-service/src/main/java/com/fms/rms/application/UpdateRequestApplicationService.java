package com.fms.rms.application;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fms.rms.constants.RMSConstants;
import com.fms.rms.domain.commands.UpdateRequestCommand;
import com.fms.rms.domain.models.UpdateRequestModel;
import com.fms.rms.models.RMSEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UpdateRequestApplicationService implements IApplicationService {

	private static final String EVENT_NAME = RMSConstants.REQUEST_UPDATE_RESOURCE_TYPE;

	@Override
	public String getServiceIdentifier() {
		return EVENT_NAME;
	}

	@Override
	public void process(RMSEvent rmsEvent) throws JsonProcessingException {
		try {

			final UpdateRequestModel requestBody = IApplicationService.getObjectMapper().readValue(rmsEvent.getBody(),
					UpdateRequestModel.class);

			final UpdateRequestCommand updateRequestCommand = UpdateRequestCommand.builder()
					.header(rmsEvent.getHeader()).body(requestBody).errors(rmsEvent.getErrors()).build();
			log.debug("Update Request Command created: {}", updateRequestCommand);

//			initiateRequestDomainService.on(initiateRequestCommand);

		} catch (JsonProcessingException exception) {

			log.error("Exception while mapping eventBody to Command: {}", exception);
		}

	}

}
