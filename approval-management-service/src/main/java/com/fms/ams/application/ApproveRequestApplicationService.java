package com.fms.ams.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fms.ams.AMSConstants;
import com.fms.ams.domain.commands.ApproveRequestCommand;
import com.fms.ams.domain.models.ApproveRequestModel;
import com.fms.ams.domain.services.ApproveRequestDomainService;
import com.fms.common.BaseEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ApproveRequestApplicationService implements IApplicationService {

	@Autowired
	private ApproveRequestDomainService approveRequestDomainService;

	private static final String EVENT_NAME = AMSConstants.REQUEST_INITIATED;

	@Override
	public String getServiceIdentifier() {
		return EVENT_NAME;
	}

	@Override
	public void process(BaseEvent event) {
		try {

			final ApproveRequestCommand approveRequestCommand = ApproveRequestCommand.builder()
					.header(event.getHeader()).body((ApproveRequestModel) event.getBody()).errors(event.getErrors())
					.build();
			log.debug("Approve Request Command created: {}", approveRequestCommand);

			approveRequestDomainService.on(approveRequestCommand);

		} catch (JsonProcessingException exception) {

			log.error("Exception while mapping eventBody to Command: {}", exception);
		}
	}
}
