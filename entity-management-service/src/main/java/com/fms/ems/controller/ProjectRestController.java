package com.fms.ems.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fms.ems.entity.Project;
import com.fms.ems.repository.ProjectRepository;

@RestController
@RequestMapping("/api")
public class ProjectRestController {

  @Autowired ProjectRepository projectRepository;

  @GetMapping("/projects")
  public ResponseEntity<List<Project>> getAllProjects() {
    try {
      final List<Project> projects = projectRepository.findAll();
      if (projects.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      } else {
        return new ResponseEntity<>(projects, HttpStatus.OK);
      }
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/project/{id}")
  public ResponseEntity<Project> getProjectById(@PathVariable String id) {
    try {
      final Optional<Project> projectOptional = projectRepository.findById(Integer.parseInt(id));
      if (projectOptional.isPresent()) {
        return new ResponseEntity<>(projectOptional.get(), HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping("/project")
  public ResponseEntity<Project> createProject(@RequestBody Project project) {
    try {
      final Project savedProject = projectRepository.save(project);
      return new ResponseEntity<>(savedProject, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/project/{id}")
  public ResponseEntity<Project> updateProject(@RequestBody Project project) {
    final Project updatedProject = projectRepository.save(project);
    try {
      return new ResponseEntity<>(updatedProject, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/project/{id}")
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
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}