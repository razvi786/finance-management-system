package com.fms.ams.domain.services;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fms.ams.AMSConstants;
import com.fms.ams.domain.commands.ApproveRequestCommand;
import com.fms.ams.domain.models.ApproveRequestModel;
import com.fms.ams.infrastructure.entity.Approval;
import com.fms.ams.infrastructure.repository.ApprovalRepository;
import com.fms.common.BaseEvent;
import com.fms.common.Header;
import com.fms.common.events.RequestApprovedEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ApproveRequestDomainService {

	@Autowired
	private ObjectMapper mapper;

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
			approval.setApprovalUuid(UUID.randomUUID());
			approval.setRequestUuid(approveRequestModel.getRequestUuid());
			approval.setApproverLevelId(approveRequestModel.getApproverLevelId());
			approval.setApproverId(approveRequestModel.getApproverId());
			approval.setComments(approveRequestModel.getComments());
			approval.setStatus(approveRequestModel.getStatus());
			approval.setCreatedDatetime(OffsetDateTime.now());
			approval.setUpdatedDatetime(OffsetDateTime.now());
			saveApproval = approvalRepository.save(approval);
			publishAMSEvent(approvalRequestCommand, saveApproval);
		} catch (JsonProcessingException e) {
			log.error("Exception in ApproveRequestDomainService", e);
		}
	}

	private BaseEvent publishAMSEvent(ApproveRequestCommand approveRequestCommand, Approval approval)
			throws JsonProcessingException {
		final Header approveRequestHeader = approveRequestCommand.getHeader();
		approveRequestHeader.setEventName(AMSConstants.REQUEST_APPROVED_EVENT);
		approveRequestHeader.setEventFrom(AMSConstants.APPROVAL_MANAGEMENT_SERVICE);
		approveRequestHeader.setEventDateTime(LocalDateTime.now());
		RequestApprovedEvent requestApprovedEvent = mapEntityToEvent(approval);
		BaseEvent amsEvent = new BaseEvent(approveRequestHeader, mapper.valueToTree(requestApprovedEvent), null);
		eventPublisher.publishEvent(amsEvent);
		log.debug("Published Event with eventName: {} and approvalUUid: {}", approveRequestHeader.getEventName(),
				requestApprovedEvent.getRequestUuid());
		return amsEvent;
	}

	private RequestApprovedEvent mapEntityToEvent(Approval publishApproval) {
		RequestApprovedEvent requestApprovedEvent = new RequestApprovedEvent();
		requestApprovedEvent.setApprovalUuid(publishApproval.getApprovalUuid());
		requestApprovedEvent.setRequestUuid(publishApproval.getRequestUuid());
		requestApprovedEvent.setApproverLevelId(publishApproval.getApproverLevelId());
		requestApprovedEvent.setComments(publishApproval.getComments());
		requestApprovedEvent.setStatus(publishApproval.getStatus());
		return requestApprovedEvent;
	}
}
