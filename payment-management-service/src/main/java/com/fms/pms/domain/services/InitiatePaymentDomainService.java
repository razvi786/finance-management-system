package com.fms.pms.domain.services;

import static com.fms.pms.constants.PmsConstants.PAYMENT_INITIATED;
import static com.fms.pms.constants.PmsConstants.PAYMENT_MANAGEMENT_SERVICE;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fms.common.BaseEvent;
import com.fms.common.Header;
import com.fms.common.enums.PaymentStatus;
import com.fms.common.events.InitiatePaymentEventBody;
import com.fms.pms.domain.commands.InitiatePaymentCommand;
import com.fms.pms.infrastructure.entity.Payment;
import com.fms.pms.infrastructure.repository.PaymentRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class InitiatePaymentDomainService {

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@Autowired
	private ObjectMapper mapper;

	@Transactional
	public void on(final InitiatePaymentCommand initiatePaymentCommand) throws JsonProcessingException {
		log.debug("Inside InitiatePaymentDomainService.on() method with Command: {}", initiatePaymentCommand);

		final Payment payment = createPayment(initiatePaymentCommand.getBody());
		log.debug("Payment is created: {}", payment);

		final BaseEvent paymentInitiatedEvent = mapToPaymentInitiatedEvent(initiatePaymentCommand, payment);

		publishEventToOutboundTopic(paymentInitiatedEvent);
	}

	Payment createPayment(final InitiatePaymentEventBody initiatePaymentEventBody) {

		Payment payment = new Payment();
		payment.setPaymentUuid(UUID.randomUUID());

		payment.setRequestUuid(initiatePaymentEventBody.getRequestUuid());
		payment.setProjectId(initiatePaymentEventBody.getProjectId());
		payment.setVendorId(initiatePaymentEventBody.getVendorId());
		payment.setAmount(initiatePaymentEventBody.getAmount());

		payment.setStatus(PaymentStatus.INITIATED);
		payment.setCreatedDatetime(OffsetDateTime.now());
		payment.setUpdatedDatetime(OffsetDateTime.now());

		return paymentRepository.save(payment);
	}

	BaseEvent mapToPaymentInitiatedEvent(final InitiatePaymentCommand initiatePaymentCommand, final Payment payment)
			throws JsonProcessingException {

		final Header header = initiatePaymentCommand.getHeader();
		header.setEventName(PAYMENT_INITIATED);
		header.setEventFrom(PAYMENT_MANAGEMENT_SERVICE);
		header.setEventTo(null);
		header.setEventDateTime(LocalDateTime.now());

		BaseEvent paymentInitiatedEvent;
		try {
			final String body = mapper.writeValueAsString(payment);

			paymentInitiatedEvent = new BaseEvent(header, mapper.valueToTree(body), null);
			log.debug("Mapped to {} event: {}", paymentInitiatedEvent.getHeader().getEventName(),
					paymentInitiatedEvent);

		} catch (JsonProcessingException exception) {

			log.error("Exception while mapping publishing event body to String: {}", exception);
			throw exception;
		}

		return paymentInitiatedEvent;
	}

	void publishEventToOutboundTopic(final BaseEvent event) {
		log.info("Inside publishEventToOutboundTopic() method");

		eventPublisher.publishEvent(event);
	}
}
