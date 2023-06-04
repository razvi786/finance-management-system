package com.fms.rms.infrastructure.listener;

import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fms.common.BaseEvent;
import com.fms.common.IApplicationService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RMSEventListener {

	@Resource(name = "applicationServiceMap")
	private Map<String, IApplicationService> applicationServiceMap;

	@Autowired
	private ObjectMapper mapper;

	@JmsListener(destination = "${aws.sqs.rms-queue-in.name}")
	public void onReceive(@Payload final String incomingMessage) throws JsonProcessingException {

		log.info("Received Message: {}", incomingMessage);

		final BaseEvent event = mapper.readValue(incomingMessage, BaseEvent.class);

		log.info("Received {} event with header: {} body: {} and errors: {}", event.getHeader().getEventName(),
				event.getHeader(), event.getBody(), event.getErrors());

		final String eventName = event.getHeader().getEventName();

		final IApplicationService applicationService = applicationServiceMap.get(eventName);

		if (Objects.nonNull(applicationService)) {
			applicationService.process(event);
		} else {
			log.error("No Application Service found with eventName: {}", eventName);
		}
	}
}
