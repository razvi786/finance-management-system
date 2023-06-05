package com.fms.pms.application;

import static com.fms.pms.constants.PmsConstants.UPDATE_PAYMENT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fms.common.BaseEvent;
import com.fms.common.IApplicationService;
import com.fms.common.events.UpdatePaymentBody;
import com.fms.pms.domain.commands.HandleUpdatePaymentCommand;
import com.fms.pms.domain.services.UpdatePaymentDomainService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UpdatePaymentService implements IApplicationService {

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private UpdatePaymentDomainService updatePaymentDomainService;

	private static final String EVENT_NAME = UPDATE_PAYMENT;

	@Override
	public String getServiceIdentifier() {
		return EVENT_NAME;
	}

	@Override
	public void process(BaseEvent event) throws JsonProcessingException {

		log.info("Inside UpdatePaymentService.process() method with Event: {}", event);

		UpdatePaymentBody updatePaymentCommandBody = mapper.treeToValue(event.getBody(), UpdatePaymentBody.class);

		final HandleUpdatePaymentCommand handleUpdatePaymentCommand = HandleUpdatePaymentCommand.builder()
				.header(event.getHeader()).body(updatePaymentCommandBody).errors(event.getErrors()).build();

		log.info("HandleUpdatePaymentCommand created: {}", handleUpdatePaymentCommand);

		updatePaymentDomainService.on(handleUpdatePaymentCommand);

	}
}
