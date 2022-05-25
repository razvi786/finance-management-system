package com.fms.rms.domain.services;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fms.rms.application.IApplicationService;
import com.fms.rms.constants.RMSConstants;
import com.fms.rms.domain.commands.InitiateRequestCommand;
import com.fms.rms.domain.models.CreateRequestModel;
import com.fms.rms.enums.StatusType;
import com.fms.rms.infrastructure.entity.Request;
import com.fms.rms.infrastructure.events.RequestChangedEvent;
import com.fms.rms.infrastructure.repository.RequestRepository;
import com.fms.rms.models.Header;
import com.fms.rms.models.RMSEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class InitiateRequestDomainService {

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@Autowired
	private RequestRepository requestRepository;

	@Transactional
	public void on(final InitiateRequestCommand initiateRequestCommand) throws JsonProcessingException {

		Request savedRequest = null;
		try {
			log.debug("Event Received in RequestDomainService with eventName: {}",
					initiateRequestCommand.getHeader().getEventName());

			CreateRequestModel createRequestModel = initiateRequestCommand.getBody();

			Request createRequest = new Request();
			createRequest.setRequestUuid(UUID.randomUUID());
			createRequest.setRaisedBy(createRequestModel.getRaisedBy());
			createRequest.setStatusType(StatusType.INITIATED);
			createRequest.setDeadlineDatetime(createRequestModel.getDeadlineDatetime());
			createRequest.setCreatedDatetime(OffsetDateTime.now());
			savedRequest = requestRepository.save(createRequest);
			publishRMSEvent(initiateRequestCommand, savedRequest);
		} catch (JsonProcessingException e) {
			log.error("Exception in InitiateRequestDomainService", e);
		}
	}

	private RMSEvent publishRMSEvent(InitiateRequestCommand initiateRequestCommand, Request requestCreated)
			throws JsonProcessingException {
		final Header initiateRequestHeader = initiateRequestCommand.getHeader();
		initiateRequestHeader.setEventName(RMSConstants.REQUEST_INITIATED);
		initiateRequestHeader.setEventFrom(RMSConstants.REQUEST_MANAGEMENT_SERVICE);
		initiateRequestHeader.setEventDateTime(LocalDateTime.now());
		RequestChangedEvent requestChangedEvent = mapEntityToEvent(requestCreated);
		String body = IApplicationService.getObjectMapper().writeValueAsString(requestChangedEvent);
		RMSEvent rmsEvent = new RMSEvent(initiateRequestHeader, body, null);
		eventPublisher.publishEvent(rmsEvent);
		log.debug("Published Event with eventName: {} and requestUUid: {}", initiateRequestHeader.getEventName(),
				requestChangedEvent.getRequestUuid());
		return rmsEvent;
	}

	private RequestChangedEvent mapEntityToEvent(Request publishRequest) {
		RequestChangedEvent requestChangedEvent = new RequestChangedEvent();
		requestChangedEvent.setRequestUuid(publishRequest.getRequestUuid());
		requestChangedEvent.setRaisedBy(publishRequest.getRaisedBy());
		requestChangedEvent.setStatusType(publishRequest.getStatusType());
		requestChangedEvent.setDeadlineDatetime(publishRequest.getDeadlineDatetime());
		return requestChangedEvent;
	}
}
