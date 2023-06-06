package com.fms.rms.domain.services;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fms.common.BaseEvent;
import com.fms.common.Header;
import com.fms.common.events.RequestChanged;
import com.fms.common.events.UpdateRequest;
import com.fms.rms.constants.RMSConstants;
import com.fms.rms.domain.commands.UpdateRequestCommand;
import com.fms.rms.infrastructure.entity.Request;
import com.fms.rms.infrastructure.repository.RequestRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UpdateRequestDomainService {

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@Autowired
	private RequestRepository requestRepository;

	@Transactional
	public void on(final UpdateRequestCommand updateRequestCommand) throws JsonProcessingException {

		Request savedRequest = null;
		try {
			log.debug("Event Received in RequestDomainService with eventName: {}",
					updateRequestCommand.getHeader().getEventName());

			UpdateRequest updateRequestModel = updateRequestCommand.getBody();
			Optional<Request> requestToBeUpdated = Optional.empty();
			if (updateRequestModel.getRequestUuid() != null) {
				requestToBeUpdated = requestRepository.findById(updateRequestModel.getRequestUuid());
			}
			if (requestToBeUpdated.isPresent()) {
				Request updateRequest = requestToBeUpdated.get();
				updateRequest.setRaisedBy(updateRequestModel.getRaisedBy());
				updateRequest.setStatus(updateRequestModel.getStatusType());
				updateRequest.setDeadlineDatetime(updateRequestModel.getDeadlineDatetime());
				updateRequest.setCreatedDatetime(OffsetDateTime.now());
				savedRequest = requestRepository.save(updateRequest);
				publishRMSEvent(updateRequestCommand, savedRequest);
			}
		} catch (JsonProcessingException e) {
			log.error("Exception in InitiateRequestDomainService", e);
		}
	}

	private BaseEvent publishRMSEvent(UpdateRequestCommand updateRequestCommand, Request requestUpdated)
			throws JsonProcessingException {
		final Header updateRequestHeader = updateRequestCommand.getHeader();
		updateRequestHeader.setEventName(RMSConstants.REQUEST_UPDATED);
		updateRequestHeader.setEventFrom(RMSConstants.REQUEST_MANAGEMENT_SERVICE);
		updateRequestHeader.setEventDateTime(LocalDateTime.now());
		RequestChanged requestChangedEvent = mapEntityToEvent(requestUpdated);
		String body = mapper.writeValueAsString(requestChangedEvent);
		BaseEvent rmsEvent = new BaseEvent(updateRequestHeader, mapper.valueToTree(body), null);
		eventPublisher.publishEvent(rmsEvent);
		log.debug("Published Event with eventName: {} and requestUUid: {}", updateRequestHeader.getEventName(),
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
