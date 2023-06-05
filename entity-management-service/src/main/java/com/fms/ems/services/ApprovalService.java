package com.fms.ems.services;

import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fms.common.entity.Approval;
import com.fms.common.enums.ApprovalStatus;
import com.fms.common.ui.responses.ApprovalsList;

@Service
public class ApprovalService {

	@Autowired
	RestTemplate restTemplate;

	@Value("${endpoint.ams}")
	private String amsEndpoint;

	@Autowired
	private UserService userService;

	@Autowired
	private ApproverLevelService approverLevelService;

	public ApprovalsList populateMetadata(ApprovalsList approvals) {
		for (Approval approval : approvals) {
			String approverName = userService.getUserNameByUserId(approval.getApproverId());
			Integer approverLevel = approverLevelService
					.getApproverLevelByApproverLevelId(approval.getApproverLevelId());
			approval.setApproverName(approverName);
			approval.setApproverLevel(approverLevel);
		}
		return approvals;
	}

	public Approval populateMetadata(Approval approval) {
		String approverName = userService.getUserNameByUserId(approval.getApproverId());
		Integer approverLevel = approverLevelService.getApproverLevelByApproverLevelId(approval.getApproverLevelId());
		approval.setApproverName(approverName);
		approval.setApproverLevel(approverLevel);
		return approval;
	}

	public ResponseEntity<Approval> approveApproval(Approval approval) {
		approval.setStatus(ApprovalStatus.APPROVED);
		return restTemplate.exchange(amsEndpoint + "/approval/" + approval.getApprovalUuid(), HttpMethod.PUT,
				new HttpEntity<Approval>(approval), Approval.class);
	}

	public ResponseEntity<Approval> rejectApproval(Approval approval) {
		approval.setStatus(ApprovalStatus.REJECTED);
		return restTemplate.exchange(amsEndpoint + "/approval/" + approval.getApprovalUuid(), HttpMethod.PUT,
				new HttpEntity<Approval>(approval), Approval.class);
	}

	public ResponseEntity<ApprovalsList> getApprovalsByRequestUuid(UUID requestUuid) {
		ApprovalsList approvals = restTemplate.getForObject(amsEndpoint + "/" + requestUuid + "/approvals",
				ApprovalsList.class);
		if (Objects.isNull(approvals)) {
			return new ResponseEntity<>(new ApprovalsList(), HttpStatus.OK);
		} else {
			populateMetadata(approvals);
			return new ResponseEntity<>(approvals, HttpStatus.OK);
		}
	}

}
