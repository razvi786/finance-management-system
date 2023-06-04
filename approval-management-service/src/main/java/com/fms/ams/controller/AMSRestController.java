package com.fms.ams.controller;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fms.ams.infrastructure.entity.Approval;
import com.fms.ams.infrastructure.repository.ApprovalRepository;
import com.fms.common.enums.ApprovalStatus;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/ams")
@CrossOrigin(origins = "*")
@Slf4j
public class AMSRestController {

	@Autowired
	ApprovalRepository approvalRepository;

	@GetMapping("/approvals")
	@Transactional
	public ResponseEntity<List<Approval>> getAllApprovals() {
		try {
			final List<Approval> approvals = approvalRepository.findAll();
			if (approvals.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(approvals, HttpStatus.OK);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/approval/{approvalUuid}")
	public ResponseEntity<Approval> getApprovalById(@PathVariable UUID approvalUuid) {
		try {
			final Optional<Approval> approvalOptional = approvalRepository.findById(approvalUuid);
			if (approvalOptional.isPresent()) {
				return new ResponseEntity<>(approvalOptional.get(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/approval")
	@Transactional
	public ResponseEntity<Approval> createApproval(@RequestBody Approval approval) {
		try {
			Approval createApproval = new Approval();
			createApproval.setApprovalUuid(UUID.randomUUID());
			createApproval.setRequestUuid(approval.getRequestUuid());
			createApproval.setApproverLevelId(approval.getApproverLevelId());
			createApproval.setApproverId(approval.getApproverId());
			createApproval.setComments(approval.getComments());
			createApproval.setStatus(ApprovalStatus.INITIATED);
			createApproval.setCreatedDatetime(OffsetDateTime.now());
			createApproval.setUpdatedDatetime(OffsetDateTime.now());
			Approval savedApproval = approvalRepository.save(createApproval);
			return new ResponseEntity<>(savedApproval, HttpStatus.CREATED);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/approval/{approvalUuid}")
	@Transactional
	public ResponseEntity<Approval> updateApproval(@PathVariable UUID approvalUuid, @RequestBody Approval approval) {
		try {
			final Optional<Approval> approvalOptional = approvalRepository.findById(approvalUuid);
			if (approvalOptional.isPresent()) {
				final Approval existingApproval = approvalOptional.get();
				existingApproval.setApproverId(approval.getApproverId());
				existingApproval.setComments(approval.getComments());
				existingApproval.setStatus(approval.getStatus());
				existingApproval.setUpdatedDatetime(OffsetDateTime.now());
				final Approval updatedApproval = approvalRepository.save(existingApproval);
				return new ResponseEntity<>(updatedApproval, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/approval/{approvalUuid}")
	@Transactional
	public ResponseEntity<Approval> deleteApproval(@PathVariable UUID approvalUuid) {
		try {
			final Optional<Approval> approvalOptional = approvalRepository.findById(approvalUuid);
			if (approvalOptional.isPresent()) {
				approvalRepository.delete(approvalOptional.get());
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{requestUuid}/approvals")
	@Transactional
	public ResponseEntity<List<Approval>> getAllApprovalsByRequestUuid(@PathVariable UUID requestUuid) {
		try {
			final List<Approval> requests = approvalRepository.findAllByRequestUuid(requestUuid);
			if (requests.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(requests, HttpStatus.OK);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
