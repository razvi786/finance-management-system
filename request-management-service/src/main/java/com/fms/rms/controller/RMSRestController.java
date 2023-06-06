package com.fms.rms.controller;

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

import com.fms.common.enums.RequestStatus;
import com.fms.rms.infrastructure.entity.Request;
import com.fms.rms.infrastructure.repository.RequestRepository;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/rms")
@CrossOrigin(origins = "*")
@Slf4j
public class RMSRestController {

	@Autowired
	RequestRepository requestRepository;

	@GetMapping("/requests")
	@Transactional
	public ResponseEntity<List<Request>> getAllRequests() {
		try {
			final List<Request> requests = requestRepository.findAll();
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

	@GetMapping("/request/{requestUuid}")
	public ResponseEntity<Request> getRequestById(@PathVariable UUID requestUuid) {
		try {
			final Optional<Request> requestOptional = requestRepository.findById(requestUuid);
			if (requestOptional.isPresent()) {
				return new ResponseEntity<>(requestOptional.get(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/request")
	@Transactional
	public ResponseEntity<Request> createRequest(@RequestBody com.fms.common.entity.Request request) {
		try {
			Request createRequest = new Request();
			createRequest.setRequestUuid(UUID.randomUUID());
			createRequest.setStatus(RequestStatus.INITIATED);
			createRequest.setCreatedDatetime(OffsetDateTime.now());
			createRequest.setUpdatedDatetime(OffsetDateTime.now());
			createRequest.setRaisedBy(request.getRaisedBy());
			createRequest.setDeadlineDatetime(request.getDeadlineDatetime());
			createRequest.setAmount(request.getAmount());
			createRequest.setDescription(request.getDescription());
			createRequest.setProjectId(request.getProjectId());
			createRequest.setVendorId(request.getVendorId());
			Request savedRequest = requestRepository.save(createRequest);
			return new ResponseEntity<>(savedRequest, HttpStatus.CREATED);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/request/{requestUuid}")
	@Transactional
	public ResponseEntity<Request> updateRequest(@PathVariable UUID requestUuid, @RequestBody Request request) {
		try {
			final Optional<Request> requestOptional = requestRepository.findById(requestUuid);
			if (requestOptional.isPresent()) {
				final Request existingRequest = requestOptional.get();
				existingRequest.setRaisedBy(request.getRaisedBy());
				existingRequest.setStatus(request.getStatus());
				existingRequest.setDeadlineDatetime(request.getDeadlineDatetime());
				existingRequest.setAmount(request.getAmount());
				existingRequest.setDescription(request.getDescription());
				existingRequest.setProjectId(request.getProjectId());
				existingRequest.setVendorId(request.getVendorId());
				final Request updatedRequest = requestRepository.save(existingRequest);
				return new ResponseEntity<>(updatedRequest, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/request/{requestUuid}")
	@Transactional
	public ResponseEntity<Request> deleteRequest(@PathVariable UUID requestUuid) {
		try {
			final Optional<Request> requestOptional = requestRepository.findById(requestUuid);
			if (requestOptional.isPresent()) {
				requestRepository.delete(requestOptional.get());
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{projectId}/requests")
	@Transactional
	public ResponseEntity<List<Request>> getAllRequestsByProjectId(@PathVariable int projectId) {
		try {
			final List<Request> requests = requestRepository.findByProjectId(projectId);
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
