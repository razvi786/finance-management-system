package com.fms.ems.controller;

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

import com.fms.common.entity.Request;
import com.fms.common.ui.responses.RequestsList;
import com.fms.ems.services.RequestService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/rms")
@CrossOrigin(origins = "*")
@Slf4j
public class RequestRestController {

	@Autowired
	RestTemplate restTemplate;

	@Value("${endpoint.rms}")
	private String rmsEndpoint;

	@Autowired
	private RequestService requestService;

	@GetMapping("/requests")
	@Transactional
	public ResponseEntity<RequestsList> getAllRequests() {
		try {
			RequestsList requests = restTemplate.getForObject(rmsEndpoint + "/requests", RequestsList.class);
			if (requests.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				requests = requestService.populateMetadata(requests);
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
			Request request = restTemplate.getForObject(rmsEndpoint + "/request/" + requestUuid, Request.class);
			if (request != null) {
				request = requestService.populateMetadata(request);
				return new ResponseEntity<>(request, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/request/{requestUuid}")
	@Transactional
	public ResponseEntity<Request> updateRequest(@PathVariable String requestUuid, @RequestBody Request request) {
		try {
			ResponseEntity<Request> response = restTemplate.exchange(rmsEndpoint + "/request/" + requestUuid,
					HttpMethod.PUT, new HttpEntity<Request>(request), Request.class);
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

	@PostMapping("/request")
	@Transactional
	public ResponseEntity<Request> createRequest(@RequestBody Request request) {
		try {
			ResponseEntity<Request> response = restTemplate.exchange(rmsEndpoint + "/request", HttpMethod.POST,
					new HttpEntity<Request>(request), Request.class);
			if (response.getStatusCode() == HttpStatus.CREATED) {
				requestService.mapAndPublishInitiateApprovalsEvent(response.getBody());
				return new ResponseEntity<>(response.getBody(), HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{projectId}/requests")
	@Transactional
	public ResponseEntity<RequestsList> getAllRequestsByProjectId(@PathVariable int projectId) {
		try {
			RequestsList requests = restTemplate.getForObject(rmsEndpoint + "/" + projectId + "/requests",
					RequestsList.class);
			if (Objects.isNull(requests)) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				requests = requestService.populateMetadata(requests);
				return new ResponseEntity<>(requests, HttpStatus.OK);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
