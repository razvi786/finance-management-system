package com.fms.pms.controller;

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

import com.fms.common.enums.PaymentStatus;
import com.fms.pms.infrastructure.entity.Payment;
import com.fms.pms.infrastructure.repository.PaymentRepository;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/pms")
@CrossOrigin(origins = "*")
@Slf4j
public class PMSRestController {

	@Autowired
	PaymentRepository paymentRepository;

	@GetMapping("/payments")
	@Transactional
	public ResponseEntity<List<Payment>> getAllPayments() {
		try {
			final List<Payment> payments = paymentRepository.findAll();
			if (payments.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
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
			final Optional<Payment> paymentOptional = paymentRepository.findById(paymentUuid);
			if (paymentOptional.isPresent()) {
				return new ResponseEntity<>(paymentOptional.get(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/payment")
	@Transactional
	public ResponseEntity<Payment> createPayment(@RequestBody com.fms.common.entity.Payment payment) {
		try {
			Payment createPayment = new Payment();
			createPayment.setPaymentUuid(UUID.randomUUID());
			createPayment.setStatus(PaymentStatus.INITIATED);

			createPayment.setRequestUuid(payment.getRequestUuid());
			createPayment.setProjectId(payment.getProjectId());
			createPayment.setVendorId(payment.getVendorId());
			createPayment.setUserId(payment.getUserId());
			createPayment.setAmount(payment.getAmount());

			createPayment.setCreatedDatetime(OffsetDateTime.now());
			createPayment.setUpdatedDatetime(OffsetDateTime.now());
			Payment savedPayment = paymentRepository.save(createPayment);
			return new ResponseEntity<>(savedPayment, HttpStatus.CREATED);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/payment/{paymentUuid}")
	@Transactional
	public ResponseEntity<Payment> updatePayment(@PathVariable UUID paymentUuid, @RequestBody Payment payment) {
		try {
			final Optional<Payment> paymentOptional = paymentRepository.findById(paymentUuid);
			if (paymentOptional.isPresent()) {
				final Payment existingPayment = paymentOptional.get();
				existingPayment.setStatus(payment.getStatus());
				existingPayment.setVendorId(payment.getVendorId());
				existingPayment.setUserId(payment.getUserId());
				existingPayment.setAmount(payment.getAmount());
				existingPayment.setTransactionId(payment.getTransactionId());

				existingPayment.setUpdatedDatetime(OffsetDateTime.now());
				final Payment updatedPayment = paymentRepository.save(existingPayment);
				return new ResponseEntity<>(updatedPayment, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/payment/{paymentUuid}")
	@Transactional
	public ResponseEntity<Payment> deletePayment(@PathVariable UUID paymentUuid) {
		try {
			final Optional<Payment> paymentOptional = paymentRepository.findById(paymentUuid);
			if (paymentOptional.isPresent()) {
				paymentRepository.delete(paymentOptional.get());
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
