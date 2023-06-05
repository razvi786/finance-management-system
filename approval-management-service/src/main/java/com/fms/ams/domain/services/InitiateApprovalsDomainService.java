package com.fms.ams.domain.services;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fms.ams.AMSConstants;
import com.fms.ams.domain.commands.InitiateApprovalsCommand;
import com.fms.ams.infrastructure.entity.Approval;
import com.fms.ams.infrastructure.repository.ApprovalRepository;
import com.fms.common.BaseEvent;
import com.fms.common.Header;
import com.fms.common.enums.ApprovalStatus;
import com.fms.common.events.ApprovalsInitiatedEventBody;
import com.fms.common.events.InitiateApprovalDetails;
import com.fms.common.events.InitiateApprovalsEventBody;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class InitiateApprovalsDomainService {

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@Autowired
	private ApprovalRepository approvalRepository;

	@Transactional
	public void on(final InitiateApprovalsCommand initiateApprovalsCommand) throws JsonProcessingException {

		try {
			log.info("Event Received in InitiateApprovalsDomainService with eventName: {}",
					initiateApprovalsCommand.getHeader().getEventName());

			InitiateApprovalsEventBody initiateApprovalsEventBody = initiateApprovalsCommand.getBody();

			List<com.fms.common.entity.Approval> savedApprovals = new ArrayList<>();
			for (InitiateApprovalDetails approvalDetail : initiateApprovalsEventBody.getApprovals()) {
				Approval approval = new Approval();
				approval.setApprovalUuid(UUID.randomUUID());
				approval.setRequestUuid(initiateApprovalsEventBody.getRequestUuid());
				approval.setApproverLevelId(approvalDetail.getApproverLevelId());
				approval.setApproverId(approvalDetail.getApproverId());

				approval.setStatus(ApprovalStatus.INITIATED);
				approval.setCreatedDatetime(OffsetDateTime.now());
				approval.setUpdatedDatetime(OffsetDateTime.now());
				approval = approvalRepository.save(approval);
				com.fms.common.entity.Approval savedApproval = new com.fms.common.entity.Approval();
				BeanUtils.copyProperties(approval, savedApproval);
				savedApprovals.add(savedApproval);
			}

			publishAMSEvent(initiateApprovalsCommand, savedApprovals);
		} catch (JsonProcessingException e) {
			log.error("Exception in InitiateApprovalsDomainService", e);
		}
	}

	private BaseEvent publishAMSEvent(InitiateApprovalsCommand initiateApprovalsCommand,
			List<com.fms.common.entity.Approval> savedApprovals) throws JsonProcessingException {
		final Header approveRequestHeader = initiateApprovalsCommand.getHeader();
		approveRequestHeader.setEventName(AMSConstants.APPROVALS_INITIATED_EVENT);
		approveRequestHeader.setEventFrom(AMSConstants.APPROVAL_MANAGEMENT_SERVICE);
		approveRequestHeader.setEventTo(AMSConstants.NOT_APPLICABLE);
		approveRequestHeader.setEventDateTime(LocalDateTime.now());
		ApprovalsInitiatedEventBody approvalsInitiatedEventBody = mapToApprovalsInitiatedEventBody(savedApprovals);
		BaseEvent amsEvent = new BaseEvent(approveRequestHeader, mapper.valueToTree(approvalsInitiatedEventBody), null);
		eventPublisher.publishEvent(amsEvent);
		return amsEvent;
	}

	private ApprovalsInitiatedEventBody mapToApprovalsInitiatedEventBody(
			List<com.fms.common.entity.Approval> savedApprovals) {
		ApprovalsInitiatedEventBody approvalsInitiatedEventBody = new ApprovalsInitiatedEventBody();
		approvalsInitiatedEventBody.setApprovals(savedApprovals);
		return approvalsInitiatedEventBody;
	}
}
