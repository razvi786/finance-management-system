package com.fms.ems.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fms.common.entity.Request;
import com.fms.common.ui.responses.RMSAllRequestsResponse;
import com.fms.ems.entity.Project;
import com.fms.ems.repository.ProjectRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProjectService {

	@Autowired
	RestTemplate restTemplate;

	@Value("${endpoint.rms}")
	private String rmsEndpoint;

	@Autowired
	private ProjectRepository projectRepo;

	public String getProjectNameByProjectId(int id) {
		Optional<Project> userOptional = projectRepo.findById(id);
		if (userOptional.isPresent()) {
			return userOptional.get().getProjectName();
		} else {
			return "";
		}
	}

	public List<Project> populateMetadata(List<Project> projects) {
		for (Project project : projects) {
			Double remainingBudget = Double.valueOf(project.getBudget());
			RMSAllRequestsResponse requests = restTemplate.getForObject(
					rmsEndpoint + "/" + project.getProjectId() + "/requests", RMSAllRequestsResponse.class);
			if (requests != null) {
				for (Request request : requests) {
					remainingBudget -= request.getAmount();
				}
			}
			project.setRemainingBudget(remainingBudget);
			log.info("Remaining Budget for project {} is {}", project.getProjectName(), project.getRemainingBudget());
		}
		return projects;
	}

	public Project populateMetadata(Project project) {
		Double remainingBudget = Double.valueOf(project.getBudget());
		RMSAllRequestsResponse requests = restTemplate
				.getForObject(rmsEndpoint + "/" + project.getProjectId() + "/requests", RMSAllRequestsResponse.class);
		if (requests != null) {
			for (Request request : requests) {
				remainingBudget -= request.getAmount();
			}
		}
		project.setRemainingBudget(remainingBudget);
		log.info("Remaining Budget for project {} is {}", project.getProjectName(), project.getRemainingBudget());
		return project;
	}
}
