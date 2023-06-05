package com.fms.ems.controller;

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.fms.common.entity.Payment;
import com.fms.common.ui.responses.PaymentsList;
import com.fms.ems.services.PaymentService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/pms")
@CrossOrigin(origins = "*")
@Slf4j
public class PaymentRestController {

	@Autowired
	private PaymentService paymentsService;

	@GetMapping("/payments")
	@Transactional
	public ResponseEntity<PaymentsList> getAllPayments() {
		try {
			PaymentsList payments = paymentsService.getAllPayments();
			if (payments.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				payments = paymentsService.populateMetadata(payments);
				return new ResponseEntity<>(payments, HttpStatus.OK);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/payment/{paymentUuid}")
	public ResponseEntity<Payment> getPaymentById(@PathVariable UUID paymentUuid) {
		try {
			Payment payment = paymentsService.getPaymentByPaymentUuid(paymentUuid);
			if (payment != null) {
				payment = paymentsService.populateMetadata(payment);
				return new ResponseEntity<>(payment, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/payment/{paymentUuid}")
	@Transactional
	public ResponseEntity<Payment> updatePayment(@PathVariable String paymentUuid, @RequestBody Payment payment) {
		try {
			ResponseEntity<Payment> response = paymentsService.updatePayment(paymentUuid, payment);
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

	@PostMapping("/payment")
	@Transactional
	public ResponseEntity<Payment> createPayment(@RequestBody Payment payment) {
		try {
			ResponseEntity<Payment> response = paymentsService.createPayment(payment);
			if (response.getStatusCode() == HttpStatus.CREATED) {
				return new ResponseEntity<>(response.getBody(), HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
