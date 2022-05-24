package com.fms.ams.domain.services;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fms.ams.domain.commands.ApproveRequestCommand;
import com.fms.ams.domain.models.ApproveRequestModel;
import com.fms.ams.infrastructure.entity.Approval;
import com.fms.ams.infrastructure.repository.ApprovalRepository;

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

	}
}
