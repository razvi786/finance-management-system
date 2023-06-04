package com.fms.ems.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fms.ems.entity.ApproverLevel;
import com.fms.ems.repository.ApproverLevelRepository;

@Service
public class ApproverLevelService {

	@Autowired
	private ApproverLevelRepository approverLevelRepo;

	public Integer getApproverLevelByApproverLevelId(int id) {
		Optional<ApproverLevel> approverLevelOptional = approverLevelRepo.findById(id);
		if (approverLevelOptional.isPresent()) {
			return approverLevelOptional.get().getLevel();
		} else {
			return 0;
		}
	}

	public List<ApproverLevel> getApproverLevelsByProjectId(int projectId) {
		return approverLevelRepo.findAllByProjectProjectIdOrderByLevel(projectId);
	}

}
