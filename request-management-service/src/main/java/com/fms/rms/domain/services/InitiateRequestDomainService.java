package com.fms.rms.domain.services;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fms.common.BaseEvent;
import com.fms.common.Header;
import com.fms.common.enums.RequestStatus;
import com.fms.common.events.CreateRequest;
import com.fms.common.events.RequestChanged;
import com.fms.rms.constants.RMSConstants;
import com.fms.rms.domain.commands.InitiateRequestCommand;
import com.fms.rms.infrastructure.entity.Request;
import com.fms.rms.infrastructure.repository.RequestRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class InitiateRequestDomainService {

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@Autowired
	private RequestRepository requestRepository;

	@Transactional
	public void on(final InitiateRequestCommand initiateRequestCommand) {

		Request savedRequest = null;
		try {
			log.debug("Event Received in RequestDomainService with eventName: {}",
					initiateRequestCommand.getHeader().getEventName());

			CreateRequest createRequestModel = initiateRequestCommand.getBody();

			Request createRequest = new Request();
			createRequest.setRequestUuid(UUID.randomUUID());
			createRequest.setRaisedBy(createRequestModel.getRaisedBy());
			createRequest.setStatus(RequestStatus.INITIATED);
			createRequest.setDeadlineDatetime(createRequestModel.getDeadlineDatetime());
			createRequest.setCreatedDatetime(OffsetDateTime.now());
			savedRequest = requestRepository.save(createRequest);
			publishRMSEvent(initiateRequestCommand, savedRequest);
		} catch (JsonProcessingException e) {
			log.error("Exception in InitiateRequestDomainService", e);
		}
	}

	private BaseEvent publishRMSEvent(InitiateRequestCommand initiateRequestCommand, Request requestCreated)
			throws JsonProcessingException {
		final Header initiateRequestHeader = initiateRequestCommand.getHeader();
		initiateRequestHeader.setEventName(RMSConstants.REQUEST_INITIATED);
		initiateRequestHeader.setEventFrom(RMSConstants.REQUEST_MANAGEMENT_SERVICE);
		initiateRequestHeader.setEventDateTime(LocalDateTime.now());
		RequestChanged requestChangedEvent = mapEntityToEvent(requestCreated);
		String body = mapper.writeValueAsString(requestChangedEvent);
		BaseEvent rmsEvent = new BaseEvent(initiateRequestHeader, body, null);
		eventPublisher.publishEvent(rmsEvent);
		log.debug("Published Event with eventName: {} and requestUUid: {}", initiateRequestHeader.getEventName(),
				requestChangedEvent.getRequestUuid());
		return rmsEvent;
	}

	private RequestChanged mapEntityToEvent(Request publishRequest) {
		RequestChanged requestChangedEvent = new RequestChanged();
		requestChangedEvent.setRequestUuid(publishRequest.getRequestUuid());
		requestChangedEvent.setRaisedBy(publishRequest.getRaisedBy());
		requestChangedEvent.setStatusType(publishRequest.getStatus());
		requestChangedEvent.setDeadlineDatetime(publishRequest.getDeadlineDatetime());
		return requestChangedEvent;
	}
}
