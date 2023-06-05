package com.fms.pms.application;

import static com.fms.pms.constants.PmsConstants.INITIATE_PAYMENT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fms.common.BaseEvent;
import com.fms.common.IApplicationService;
import com.fms.common.events.InitiatePaymentEventBody;
import com.fms.pms.domain.commands.InitiatePaymentCommand;
import com.fms.pms.domain.services.InitiatePaymentDomainService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class InitiatePaymentService implements IApplicationService {

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private InitiatePaymentDomainService initiatePaymentDomainService;

	private static final String EVENT_NAME = INITIATE_PAYMENT;

	@Override
	public String getServiceIdentifier() {
		return EVENT_NAME;
	}

	@Override
	public void process(BaseEvent event) throws JsonProcessingException {

		log.info("Inside InitiatePaymentService.process() method with Event: {}", event);

		InitiatePaymentEventBody initiatePaymentCommandBody = mapper.treeToValue(event.getBody(),
				InitiatePaymentEventBody.class);

		final InitiatePaymentCommand initiatePaymentCommand = InitiatePaymentCommand.builder().header(event.getHeader())
				.body(initiatePaymentCommandBody).errors(event.getErrors()).build();

		log.info("InitiatePaymentCommand created: {}", initiatePaymentCommand);

		initiatePaymentDomainService.on(initiatePaymentCommand);

	}
}
