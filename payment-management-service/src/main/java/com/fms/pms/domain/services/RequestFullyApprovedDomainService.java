package com.fms.pms.domain.services;

import static com.fms.pms.constants.PmsConstants.PAYMENT_INITIATED;
import static com.fms.pms.constants.PmsConstants.PAYMENT_MANAGEMENT_SERVICE;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fms.pms.application.IApplicationService;
import com.fms.pms.domain.commands.HandleRequestFullyApprovedCommand;
import com.fms.pms.enums.Status;
import com.fms.pms.events.PaymentInitiatedBody;
import com.fms.pms.events.RequestFullyApprovedBody;
import com.fms.pms.infrastructure.entity.Payment;
import com.fms.pms.infrastructure.repository.PaymentRepository;
import com.fms.pms.models.FmsEvent;
import com.fms.pms.models.Header;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RequestFullyApprovedDomainService {

  @Autowired private PaymentRepository paymentRepository;

  @Autowired private ApplicationEventPublisher eventPublisher;

  @Transactional
  public void on(final HandleRequestFullyApprovedCommand requestFullyApprovedCommand)
      throws JsonProcessingException {
    log.debug(
        "Inside RequestFullyCompletedDomainService.on() method with Command: {}",
        requestFullyApprovedCommand);

    final Payment payment = persistPaymentToDatabase(requestFullyApprovedCommand.getBody());
    log.debug("Payment is inserted to database: {}", payment);

    final FmsEvent paymentInitiatedEvent =
        mapToPaymentInitiatedEvent(requestFullyApprovedCommand, payment);

    publishEventToOutboundTopic(paymentInitiatedEvent);
  }

  Payment persistPaymentToDatabase(final RequestFullyApprovedBody requestFullyApprovedBody) {
    log.info("Inside persistPaymentToDatabase() method");

    final Payment payment = new Payment();
    payment.setPaymentUuid(UUID.randomUUID());
    payment.setVendorId(requestFullyApprovedBody.getVendorId());
    payment.setAmount(requestFullyApprovedBody.getRequestAmount());
    payment.setStatus(Status.INITIATED);
    payment.setCreatedDatetime(OffsetDateTime.now());
    payment.setUpdatedDatetime(OffsetDateTime.now());
    return paymentRepository.save(payment);
  }

  FmsEvent mapToPaymentInitiatedEvent(
      final HandleRequestFullyApprovedCommand requestFullyApprovedCommand, final Payment payment)
      throws JsonProcessingException {
    log.info("Inside mapToPaymentInitiatedEvent() method");

    final Header header = requestFullyApprovedCommand.getHeader();
    header.setEventName(PAYMENT_INITIATED);
    header.setEventFrom(PAYMENT_MANAGEMENT_SERVICE);
    header.setEventTo(null);
    header.setEventDateTime(LocalDateTime.now());

    final PaymentInitiatedBody paymentInitiatedBody = new PaymentInitiatedBody();
    BeanUtils.copyProperties(requestFullyApprovedCommand.getBody(), paymentInitiatedBody);
    paymentInitiatedBody.setPaymentUuid(payment.getPaymentUuid());

    FmsEvent paymentInitiatedEvent;
    try {
      final String body =
          IApplicationService.getObjectMapper().writeValueAsString(paymentInitiatedBody);

      paymentInitiatedEvent = new FmsEvent(header, body, null);
      log.debug(
          "Mapped to {} event: {}",
          paymentInitiatedEvent.getHeader().getEventName(),
          paymentInitiatedEvent);

    } catch (JsonProcessingException exception) {

      log.error("Exception while mapping publishing event body to String: {}", exception);
      throw exception;
    }

    return paymentInitiatedEvent;
  }

  void publishEventToOutboundTopic(final FmsEvent event) {
    log.info("Inside publishEventToOutboundTopic() method");

    eventPublisher.publishEvent(event);
  }
}
