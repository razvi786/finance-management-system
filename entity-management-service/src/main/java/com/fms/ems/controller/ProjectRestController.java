package com.fms.ems.controller;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fms.ems.entity.Project;
import com.fms.ems.entity.User;
import com.fms.ems.repository.ProjectRepository;
import com.fms.ems.repository.UserRepository;
import com.fms.ems.services.ProjectService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/ems")
@CrossOrigin(origins = "*")
@Slf4j
public class ProjectRestController {

	@Autowired
	ProjectRepository projectRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ProjectService projectService;

	@GetMapping("/projects")
	public ResponseEntity<List<Project>> getAllProjects() {
		try {
			List<Project> projects = projectRepository.findAll();
			if (projects.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				projects = projectService.populateMetadata(projects);
				return new ResponseEntity<>(projects, HttpStatus.OK);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/project/{id}")
	public ResponseEntity<Project> getProjectById(@PathVariable String id) {
		try {
			final Optional<Project> projectOptional = projectRepository.findById(Integer.parseInt(id));
			if (projectOptional.isPresent()) {
				Project project = projectOptional.get();
				project = projectService.populateMetadata(project);
				return new ResponseEntity<>(project, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/project")
	@Transactional
	public ResponseEntity<Project> createProject(@RequestBody Project project) {
		try {
			if (project.getUser() != null) {
				final Optional<User> userOptional = userRepository.findById(project.getUser().getUserId());
				if (userOptional.isPresent()) {
					final User user = userOptional.get();
					project.setUser(user);
				} else {
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
			}
			Project savedProject = projectRepository.save(project);
			savedProject = projectService.populateMetadata(savedProject);
			return new ResponseEntity<>(savedProject, HttpStatus.CREATED);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/project/{id}")
	@Transactional
	public ResponseEntity<Project> updateProject(@PathVariable String id, @RequestBody Project project) {
		try {
			final Optional<Project> projectOptional = projectRepository.findById(Integer.parseInt(id));
			if (projectOptional.isPresent()) {
				Project updatedProject = projectRepository.save(project);
				updatedProject = projectService.populateMetadata(updatedProject);
				return new ResponseEntity<>(updatedProject, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/project/{id}")
	@Transactional
	public ResponseEntity<Project> deleteProject(@PathVariable String id) {
		try {
			final Optional<Project> projectOptional = projectRepository.findById(Integer.parseInt(id));
			if (projectOptional.isPresent()) {
				projectRepository.delete(projectOptional.get());
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
