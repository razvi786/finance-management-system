package com.fms.ams.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fms.ams.AMSConstants;
import com.fms.ams.domain.commands.InitiateApprovalsCommand;
import com.fms.ams.domain.services.InitiateApprovalsDomainService;
import com.fms.common.BaseEvent;
import com.fms.common.events.InitiateApprovalsEventBody;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class InitiateApprovalsApplicationService implements IApplicationService {

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private InitiateApprovalsDomainService initiateApprovalsDomainService;

	private static final String EVENT_NAME = AMSConstants.INITIATE_APPROVALS_EVENT;

	@Override
	public String getServiceIdentifier() {
		return EVENT_NAME;
	}

	@Override
	public void process(BaseEvent event) {
		try {
			InitiateApprovalsEventBody initiateApprovalsCommandBody = mapper.treeToValue(event.getBody(),
					InitiateApprovalsEventBody.class);

			final InitiateApprovalsCommand initiateApprovalsCommand = InitiateApprovalsCommand.builder()
					.header(event.getHeader()).body(initiateApprovalsCommandBody).errors(event.getErrors()).build();
			log.info("Initiate Approvals Command created: {}", initiateApprovalsCommand);

			initiateApprovalsDomainService.on(initiateApprovalsCommand);

		} catch (JsonProcessingException exception) {

			log.error("Exception while mapping eventBody to Command: {}", exception);
		}
	}
}
