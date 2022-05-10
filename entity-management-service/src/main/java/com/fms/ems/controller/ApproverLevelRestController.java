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

import com.fms.ems.entity.ApproverLevel;
import com.fms.ems.repository.ApproverLevelRepository;

@RestController
@RequestMapping("/api")
public class ApproverLevelRestController {

  @Autowired ApproverLevelRepository approverLevelRepository;

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
      final Optional<ApproverLevel> approverLevelOptional =
          approverLevelRepository.findById(Integer.parseInt(id));
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
  public ResponseEntity<ApproverLevel> createApproverLevel(
      @RequestBody ApproverLevel approverLevel) {
    try {
      final ApproverLevel savedApproverLevel = approverLevelRepository.save(approverLevel);
      return new ResponseEntity<>(savedApproverLevel, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/approver-level/{id}")
  public ResponseEntity<ApproverLevel> updateApproverLevel(
      @RequestBody ApproverLevel approverLevel) {
    final ApproverLevel updatedApproverLevel = approverLevelRepository.save(approverLevel);
    try {
      return new ResponseEntity<>(updatedApproverLevel, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/approver-level/{id}")
  public ResponseEntity<ApproverLevel> deleteApproverLevel(@PathVariable String id) {
    try {
      final Optional<ApproverLevel> approverLevelOptional =
          approverLevelRepository.findById(Integer.parseInt(id));
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
}
