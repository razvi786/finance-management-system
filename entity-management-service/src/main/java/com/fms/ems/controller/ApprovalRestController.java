package com.fms.ems.controller;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fms.common.entity.Approval;
import com.fms.common.entity.Request;
import com.fms.common.enums.RequestStatus;
import com.fms.common.ui.responses.ApprovalsList;
import com.fms.ems.entity.ApproverLevel;
import com.fms.ems.services.ApprovalService;
import com.fms.ems.services.ApproverLevelService;
import com.fms.ems.services.RequestService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/ams")
@CrossOrigin(origins = "*")
@Slf4j
public class ApprovalRestController {

	@Autowired
	RestTemplate restTemplate;

	@Value("${endpoint.ams}")
	private String amsEndpoint;

	@Autowired
	private ApprovalService approvalService;

	@Autowired
	private RequestService requestService;

	@Autowired
	private ApproverLevelService approverLevelService;

	@GetMapping("/approvals")
	@Transactional
	public ResponseEntity<ApprovalsList> getAllApprovals() {
		try {
			ApprovalsList approvals = restTemplate.getForObject(amsEndpoint + "/approvals", ApprovalsList.class);
			if (approvals.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				approvals = approvalService.populateMetadata(approvals);
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
			Approval approval = restTemplate.getForObject(amsEndpoint + "/approval/" + approvalUuid, Approval.class);
			if (approval != null) {
				approval = approvalService.populateMetadata(approval);
				return new ResponseEntity<>(approval, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/approval/{approvalUuid}")
	@Transactional
	public ResponseEntity<Approval> updateApproval(@PathVariable String approvalUuid, @RequestBody Approval approval) {
		try {
			ResponseEntity<Approval> response = restTemplate.exchange(amsEndpoint + "/approval/" + approvalUuid,
					HttpMethod.PUT, new HttpEntity<Approval>(approval), Approval.class);
			if (response.getStatusCode() == HttpStatus.OK) {
				return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
			ResponseEntity<Approval> response = restTemplate.exchange(amsEndpoint + "/approval", HttpMethod.POST,
					new HttpEntity<Approval>(approval), Approval.class);
			if (response.getStatusCode() == HttpStatus.CREATED) {
				return new ResponseEntity<>(response.getBody(), HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{requestUuid}/approvals")
	@Transactional
	public ResponseEntity<ApprovalsList> getAllApprovalsByRequestUuid(@PathVariable UUID requestUuid) {
		try {
			ApprovalsList approvals = restTemplate.getForObject(amsEndpoint + "/" + requestUuid + "/approvals",
					ApprovalsList.class);
			if (Objects.isNull(approvals)) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				approvals = approvalService.populateMetadata(approvals);
				return new ResponseEntity<>(approvals, HttpStatus.OK);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/{requestUuid}/approve-request")
	@Transactional
	public ResponseEntity<Approval> approveRequest(@PathVariable UUID requestUuid, @RequestBody Approval approval) {
		Integer approverLevel = approval.getApproverLevel();
		try {
			ResponseEntity<Approval> approvalResponse = approvalService.approveApproval(approval);
			Request request = requestService.getRequestByRequestUuid(requestUuid);
			List<ApproverLevel> approverLevels = approverLevelService
					.getApproverLevelsByProjectId(request.getProjectId());
			if (approverLevels.size() == approverLevel) {
				request.setStatus(RequestStatus.APPROVED);

			} else {
				request.setStatus(RequestStatus.PARTIALLY_APPROVED);
			}
			ResponseEntity<Request> requestResponse = requestService.updateRequest(requestUuid, request);
			if (approvalResponse.getStatusCode() == HttpStatus.OK && requestResponse.getStatusCode() == HttpStatus.OK) {
				if (requestResponse.getBody().getStatus() == RequestStatus.APPROVED) {
					requestService.mapAndPublishInitiatePaymentEvent(approval, requestResponse.getBody());
				}
				return new ResponseEntity<>(approvalResponse.getBody(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/{requestUuid}/reject-request")
	@Transactional
	public ResponseEntity<Approval> rejectRequest(@PathVariable UUID requestUuid, @RequestBody Approval approval) {
		try {
			ResponseEntity<Approval> approvalResponse = approvalService.rejectApproval(approval);
			ResponseEntity<ApprovalsList> approvalsList = approvalService.getApprovalsByRequestUuid(requestUuid);
			for (Approval a : approvalsList.getBody()) {
				if (a.getApproverLevel() > approval.getApproverLevel()) {
					approvalService.rejectApproval(a);
				}
			}
			Request request = requestService.getRequestByRequestUuid(requestUuid);
			request.setStatus(RequestStatus.REJECTED);
			ResponseEntity<Request> requestResponse = requestService.updateRequest(requestUuid, request);
			if (approvalResponse.getStatusCode() == HttpStatus.OK && requestResponse.getStatusCode() == HttpStatus.OK) {
				return new ResponseEntity<>(approvalResponse.getBody(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
