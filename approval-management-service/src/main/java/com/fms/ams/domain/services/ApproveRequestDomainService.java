package com.fms.ams.domain.services;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fms.ams.AMSConstants;
import com.fms.ams.application.IApplicationService;
import com.fms.ams.domain.commands.ApproveRequestCommand;
import com.fms.ams.domain.models.ApproveRequestModel;
import com.fms.ams.infrastructure.entity.Approval;
import com.fms.ams.infrastructure.events.RequestApprovedEvent;
import com.fms.ams.infrastructure.repository.ApprovalRepository;
import com.fms.ams.models.AMSEvent;
import com.fms.ams.models.Header;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ApproveRequestDomainService {

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@Autowired
	private ApprovalRepository approvalRepository;

	@Transactional
	public void on(final ApproveRequestCommand approvalRequestCommand) throws JsonProcessingException {

		Approval saveApproval = null;
		try {
			log.debug("Event Received in ApproveRequestDomainService with eventName: {}",
					approvalRequestCommand.getHeader().getEventName());

			ApproveRequestModel approveRequestModel = approvalRequestCommand.getBody();

			Approval approval = new Approval();
			approval.setRequestUuid(approveRequestModel.getRequestUuid());
			approval.setApproverLevelUuid(approveRequestModel.getApproverLevelUuid());
			approval.setApproverUuid(approveRequestModel.getApproverUuid());
			approval.setComments(approveRequestModel.getComments());
			approval.setStatusType(approveRequestModel.getStatusType());
			approval.setCreatedDatetime(OffsetDateTime.now());
			approval.setUpdatedDatetime(OffsetDateTime.now());
			saveApproval = approvalRepository.save(approval);
			publishAMSEvent(approvalRequestCommand, saveApproval);
		} catch (JsonProcessingException e) {
			log.error("Exception in ApproveRequestDomainService", e);
		}
	}

	private AMSEvent publishAMSEvent(ApproveRequestCommand approveRequestCommand, Approval approval)
			throws JsonProcessingException {
		final Header approveRequestHeader = approveRequestCommand.getHeader();
		approveRequestHeader.setEventName(AMSConstants.REQUEST_APPROVED_EVENT);
		approveRequestHeader.setEventFrom(AMSConstants.APPROVAL_MANAGEMENT_SERVICE);
		approveRequestHeader.setEventDateTime(LocalDateTime.now());
		RequestApprovedEvent requestApprovedEvent = mapEntityToEvent(approval);
		String body = IApplicationService.getObjectMapper().writeValueAsString(requestApprovedEvent);
		AMSEvent amsEvent = new AMSEvent(approveRequestHeader, body, null);
		eventPublisher.publishEvent(amsEvent);
		log.debug("Published Event with eventName: {} and approvalUUid: {}", approveRequestHeader.getEventName(),
				requestApprovedEvent.getRequestUuid());
		return amsEvent;
	}

	private RequestApprovedEvent mapEntityToEvent(Approval publishApproval) {
		RequestApprovedEvent requestApprovedEvent = new RequestApprovedEvent();
		requestApprovedEvent.setApprovalUuid(publishApproval.getApprovalUuid());
		requestApprovedEvent.setRequestUuid(publishApproval.getRequestUuid());
		requestApprovedEvent.setApproverLevelUuid(publishApproval.getApproverLevelUuid());
		requestApprovedEvent.setComments(publishApproval.getComments());
		requestApprovedEvent.setStatusType(publishApproval.getStatusType());
		return requestApprovedEvent;
	}
}
