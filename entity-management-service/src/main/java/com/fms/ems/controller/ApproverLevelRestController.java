package com.fms.ems.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
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

import com.fms.ems.entity.ApproverLevel;
import com.fms.ems.entity.Project;
import com.fms.ems.entity.User;
import com.fms.ems.repository.ApproverLevelRepository;
import com.fms.ems.repository.ProjectRepository;
import com.fms.ems.repository.UserRepository;
import com.fms.ems.services.NotificationService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/ems")
@CrossOrigin(origins = "*")
@Slf4j
public class ApproverLevelRestController {

	@Autowired
	ApproverLevelRepository approverLevelRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ProjectRepository projectRepository;

	@Autowired
	NotificationService notificationService;

	@GetMapping("/approver-levels")
	public ResponseEntity<List<ApproverLevel>> getAllApproverLevels() {
		try {
			final List<ApproverLevel> approverLevels = approverLevelRepository.findAll();
			if (approverLevels.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(approverLevels, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/approver-level/{id}")
	public ResponseEntity<ApproverLevel> getApproverLevelById(@PathVariable String id) {
		try {
			final Optional<ApproverLevel> approverLevelOptional = approverLevelRepository
					.findById(Integer.parseInt(id));
			if (approverLevelOptional.isPresent()) {
				return new ResponseEntity<>(approverLevelOptional.get(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/approver-level")
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public ResponseEntity<ApproverLevel> createApproverLevel(@RequestBody ApproverLevel approverLevel) {
		try {
			if (approverLevel.getProject() != null) {
				final Optional<Project> projectOptional = projectRepository
						.findById(approverLevel.getProject().getProjectId());
				if (projectOptional.isPresent()) {
					final Project project = projectOptional.get();
					approverLevel.setProject(project);

					notificationService.mapAndSendAppNotificationForApproverLevelAssigned(approverLevel);
				} else {
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
			}
			if (approverLevel.getApprover() != null) {
				final Optional<User> userOptional = userRepository.findById(approverLevel.getApprover().getUserId());
				if (userOptional.isPresent()) {
					final User user = userOptional.get();
					approverLevel.setApprover(user);
				} else {
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
			}
			final ApproverLevel savedApproverLevel = approverLevelRepository.save(approverLevel);
			return new ResponseEntity<>(savedApproverLevel, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/approver-level/{id}")
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public ResponseEntity<ApproverLevel> updateApproverLevel(@PathVariable String id,
			@RequestBody ApproverLevel approverLevel) {
		try {
			final Optional<ApproverLevel> approverLevelOptional = approverLevelRepository
					.findById(Integer.parseInt(id));
			if (approverLevelOptional.isPresent()) {
				log.info("Updating Approver Level {}", approverLevel);
				final ApproverLevel updatedApproverLevel = approverLevelRepository.save(approverLevel);

				notificationService.mapAndSendAppNotificationForApproverLevelAssigned(approverLevel);
				return new ResponseEntity<>(updatedApproverLevel, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/approver-level/{id}")
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public ResponseEntity<ApproverLevel> deleteApproverLevel(@PathVariable String id) {
		try {
			final Optional<ApproverLevel> approverLevelOptional = approverLevelRepository
					.findById(Integer.parseInt(id));
			if (approverLevelOptional.isPresent()) {
				approverLevelRepository.delete(approverLevelOptional.get());
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/project/{projectId}/approver-levels")
	public ResponseEntity<List<ApproverLevel>> getApproverLevelsByProjectId(@PathVariable String projectId) {
		try {
			final List<ApproverLevel> approverLevels = approverLevelRepository
					.findAllByProjectProjectIdOrderByLevel(Integer.valueOf(projectId));
			if (approverLevels.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(approverLevels, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
