package com.fms.ems.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fms.common.BaseEvent;
import com.fms.common.Header;
import com.fms.common.entity.Request;
import com.fms.common.events.CreateRequest;
import com.fms.common.ui.responses.RMSAllRequestsResponse;
import com.fms.ems.application.IApplicationService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RequestService {

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@Autowired
	private UserService userService;

	@Autowired
	private ProjectService projectService;

	public CreateRequest mapAndPublishCreateRequestEvent(Request request) throws JsonProcessingException {
		CreateRequest outgoingRequest = new CreateRequest();
		outgoingRequest.setRaisedBy(request.getRaisedBy());
		outgoingRequest.setDeadlineDatetime(request.getDeadlineDatetime());
		outgoingRequest.setAmount(request.getAmount());
		outgoingRequest.setDescription(request.getDescription());
		outgoingRequest.setProjectId(request.getProjectId());
		publishEvent(outgoingRequest);
		return outgoingRequest;
	}

	private BaseEvent publishEvent(CreateRequest request) throws JsonProcessingException {
		final Header header = new Header();
		header.setEventName("CreateRequest");
		header.setEventFrom("UI");
		header.setEventDateTime(LocalDateTime.now());
		String body = IApplicationService.getObjectMapper().writeValueAsString(request);
		BaseEvent rmsEvent = new BaseEvent(header, body, null);
		eventPublisher.publishEvent(rmsEvent);
		log.info("Published {} Event : {}", header.getEventName(), request);
		return rmsEvent;
	}

	public RMSAllRequestsResponse populateMetadata(RMSAllRequestsResponse requests) {
		for (Request request : requests) {
			String userName = userService.getUserNameByUserId(request.getRaisedBy());
			String projectName = projectService.getProjectNameByProjectId(request.getProjectId());
			request.setRaisedByName(userName);
			request.setProjectName(projectName);
		}
		return requests;
	}

	public Request populateMetadata(Request request) {
		String userName = userService.getUserNameByUserId(request.getRaisedBy());
		String projectName = projectService.getProjectNameByProjectId(request.getProjectId());
		request.setRaisedByName(userName);
		request.setProjectName(projectName);
		return request;
	}

}
