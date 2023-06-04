package com.fms.ems.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fms.common.BaseEvent;
import com.fms.common.Header;
import com.fms.common.entity.Request;
import com.fms.common.events.CreateRequest;
import com.fms.common.events.InitiateApprovalDetails;
import com.fms.common.events.InitiateApprovalsEventBody;
import com.fms.common.ui.responses.RequestsList;
import com.fms.ems.EMSConstants;
import com.fms.ems.entity.ApproverLevel;

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

	@Autowired
	private ApproverLevelService approverLevelService;

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

	private BaseEvent publishEvent(CreateRequest request) {
		final Header header = new Header();
		header.setEventName("CreateRequest");
		header.setEventFrom("UI");
		header.setEventDateTime(LocalDateTime.now());
		BaseEvent rmsEvent = new BaseEvent(header, request, null);
		eventPublisher.publishEvent(rmsEvent);
		log.info("Published {} Event : {}", header.getEventName(), request);
		return rmsEvent;
	}

	private BaseEvent publishEvent(InitiateApprovalsEventBody initiateApprovalsEventBody) {
		final Header header = new Header();
		header.setEventName(EMSConstants.INITIATE_APPROVALS_EVENT);
		header.setEventFrom(EMSConstants.ENTITY_MANAGEMENT_SERVICE);
		header.setEventTo(EMSConstants.APPROVAL_MANAGEMENT_SERVICE);
		header.setEventDateTime(LocalDateTime.now());
		BaseEvent rmsEvent = new BaseEvent(header, initiateApprovalsEventBody, null);
		eventPublisher.publishEvent(rmsEvent);
		log.info("Published {} Event : {}", header.getEventName(), initiateApprovalsEventBody);
		return rmsEvent;
	}

	public RequestsList populateMetadata(RequestsList requests) {
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

	public void mapAndPublishInitiateApprovalsEvent(Request savedRequest) {
		InitiateApprovalsEventBody initiateApprovalsEventBody = new InitiateApprovalsEventBody();
		initiateApprovalsEventBody.setRequestUuid(savedRequest.getRequestUuid());
		List<InitiateApprovalDetails> approvalDetails = new ArrayList<>();

		List<ApproverLevel> approverLevels = approverLevelService
				.getApproverLevelsByProjectId(savedRequest.getProjectId());
		for (ApproverLevel approverLevel : approverLevels) {
			InitiateApprovalDetails approvalDetail = new InitiateApprovalDetails();
			approvalDetail.setApproverLevelId(approverLevel.getApproverLevelId());
			approvalDetail.setApproverId(approverLevel.getApprover().getUserId());
			approvalDetails.add(approvalDetail);
		}
		initiateApprovalsEventBody.setApprovals(approvalDetails);
		publishEvent(initiateApprovalsEventBody);
	}

}
