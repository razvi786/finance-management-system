package com.fms.ems.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fms.common.entity.Approval;
import com.fms.common.ui.responses.ApprovalsList;

@Service
public class ApprovalService {

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

}
