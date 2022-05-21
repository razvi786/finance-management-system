package com.fms.ams.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fms.ams.domain.commands.ApproveRequestCommand;
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

	}
}
