package com.fms.ems.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fms.common.BaseEvent;
import com.fms.common.Header;
import com.fms.common.entity.Approval;
import com.fms.common.entity.Request;
import com.fms.common.events.CreateRequest;
import com.fms.common.events.InitiateApprovalDetails;
import com.fms.common.events.InitiateApprovalsEventBody;
import com.fms.common.events.InitiatePaymentEventBody;
import com.fms.common.ui.responses.RequestsList;
import com.fms.ems.EMSConstants;
import com.fms.ems.entity.ApproverLevel;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RequestService {

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@Autowired
	private UserService userService;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private ApproverLevelService approverLevelService;

	@Autowired
	private VendorService vendorService;

	@Autowired
	RestTemplate restTemplate;

	@Value("${endpoint.rms}")
	private String rmsEndpoint;

	public Request getRequestByRequestUuid(UUID requestUuid) {
		return restTemplate.getForObject(rmsEndpoint + "/request/" + requestUuid, Request.class);
	}

	public ResponseEntity<Request> updateRequest(UUID requestUuid, Request request) {
		return restTemplate.exchange(rmsEndpoint + "/request/" + requestUuid, HttpMethod.PUT,
				new HttpEntity<Request>(request), Request.class);
	}

	public RequestsList populateMetadata(RequestsList requests) {
		for (Request request : requests) {
			String userName = userService.getUserNameByUserId(request.getRaisedBy());
			String projectName = projectService.getProjectNameByProjectId(request.getProjectId());
			String vendorName = vendorService.getVendorNameByVendorId(request.getVendorId());
			request.setRaisedByName(userName);
			request.setProjectName(projectName);
			request.setVendorName(vendorName);
		}
		return requests;
	}

	public Request populateMetadata(Request request) {
		String userName = userService.getUserNameByUserId(request.getRaisedBy());
		String projectName = projectService.getProjectNameByProjectId(request.getProjectId());
		String vendorName = vendorService.getVendorNameByVendorId(request.getVendorId());
		request.setRaisedByName(userName);
		request.setProjectName(projectName);
		request.setVendorName(vendorName);
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

	public void mapAndPublishInitiatePaymentEvent(Approval approval, Request request) {
		InitiatePaymentEventBody initiatePaymentEventBody = new InitiatePaymentEventBody();
		initiatePaymentEventBody.setRequestUuid(approval.getRequestUuid());
		initiatePaymentEventBody.setProjectId(request.getProjectId());
		initiatePaymentEventBody.setVendorId(request.getVendorId());
		initiatePaymentEventBody.setAmount(request.getAmount());
		publishEvent(initiatePaymentEventBody);
	}

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

	private BaseEvent publishEvent(InitiateApprovalsEventBody initiateApprovalsEventBody) {
		final Header header = new Header();
		header.setEventName(EMSConstants.INITIATE_APPROVALS_EVENT);
		header.setEventFrom(EMSConstants.ENTITY_MANAGEMENT_SERVICE);
		header.setEventTo(EMSConstants.APPROVAL_MANAGEMENT_SERVICE);
		header.setEventDateTime(LocalDateTime.now());
		BaseEvent initiateApprovalsEvent = new BaseEvent(header, mapper.valueToTree(initiateApprovalsEventBody), null);
		eventPublisher.publishEvent(initiateApprovalsEvent);
		log.info("Published {} Event : {}", header.getEventName(), initiateApprovalsEventBody);
		return initiateApprovalsEvent;
	}

	private BaseEvent publishEvent(InitiatePaymentEventBody initiatePaymentEventBody) {
		final Header header = new Header();
		header.setEventName(EMSConstants.INITIATE_PAYMENT_EVENT);
		header.setEventFrom(EMSConstants.ENTITY_MANAGEMENT_SERVICE);
		header.setEventTo(EMSConstants.PAYMENT_MANAGEMENT_SERVICE);
		header.setEventDateTime(LocalDateTime.now());
		BaseEvent initiatePaymentEvent = new BaseEvent(header, mapper.valueToTree(initiatePaymentEventBody), null);
		eventPublisher.publishEvent(initiatePaymentEvent);
		log.info("Published {} Event : {}", header.getEventName(), initiatePaymentEventBody);
		return initiatePaymentEvent;
	}

	private BaseEvent publishEvent(CreateRequest request) {
		final Header header = new Header();
		header.setEventName("CreateRequest");
		header.setEventFrom("UI");
		header.setEventDateTime(LocalDateTime.now());
		BaseEvent rmsEvent = new BaseEvent(header, mapper.valueToTree(request), null);
		eventPublisher.publishEvent(rmsEvent);
		log.info("Published {} Event : {}", header.getEventName(), request);
		return rmsEvent;
	}
}
