package com.fms.pms.domain.services;

import static com.fms.pms.constants.PmsConstants.PAYMENT_MANAGEMENT_SERVICE;
import static com.fms.pms.constants.PmsConstants.PAYMENT_UPDATED;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fms.pms.application.IApplicationService;
import com.fms.pms.domain.commands.HandleUpdatePaymentCommand;
import com.fms.pms.events.PaymentUpdatedBody;
import com.fms.pms.events.UpdatePaymentBody;
import com.fms.pms.infrastructure.entity.Payment;
import com.fms.pms.infrastructure.repository.PaymentRepository;
import com.fms.pms.models.FmsEvent;
import com.fms.pms.models.Header;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UpdatePaymentDomainService {

  @Autowired private PaymentRepository paymentRepository;

  @Autowired private ApplicationEventPublisher eventPublisher;

  @Transactional
  public void on(final HandleUpdatePaymentCommand handleUpdatePaymentCommand)
      throws JsonProcessingException {
    log.debug(
        "Inside UpdatePaymentDomainService.on() method with Command: {}",
        handleUpdatePaymentCommand);

    final Payment payment = updatePayment(handleUpdatePaymentCommand.getBody());
    log.debug("Payment is updated: {}", payment);

    final FmsEvent paymentUpdatedEvent =
        mapToPaymentUpdatedEvent(handleUpdatePaymentCommand, payment);

    publishEventToOutboundTopic(paymentUpdatedEvent);
  }

  Payment updatePayment(final UpdatePaymentBody updatePaymentBody) {
    log.info("Inside updatePayment() method");

    final Optional<Payment> paymentOptional =
        paymentRepository.findById(updatePaymentBody.getPaymentUuid());
    if (paymentOptional.isPresent()) {
      final Payment payment = paymentOptional.get();
      payment.setStatus(updatePaymentBody.getStatus());
      payment.setTransactionId(updatePaymentBody.getTransactionId());
      payment.setUpdatedDatetime(OffsetDateTime.now());
      return paymentRepository.save(payment);

    } else {
      log.error(
          "Payment not present in Database with PaymentUuid: {}",
          updatePaymentBody.getPaymentUuid());
      throw new RuntimeException(
          "Payment not present in Database with PaymentUuid: "
              + updatePaymentBody.getPaymentUuid());
    }
  }

  FmsEvent mapToPaymentUpdatedEvent(
      final HandleUpdatePaymentCommand handleUpdatePaymentCommand, final Payment payment)
      throws JsonProcessingException {
    log.info("Inside mapToPaymentUpdatedEvent() method");

    final Header header = handleUpdatePaymentCommand.getHeader();
    header.setEventName(PAYMENT_UPDATED);
    header.setEventFrom(PAYMENT_MANAGEMENT_SERVICE);
    header.setEventTo(null);
    header.setEventDateTime(LocalDateTime.now());

    final PaymentUpdatedBody paymentUpdatedBody = new PaymentUpdatedBody();
    BeanUtils.copyProperties(handleUpdatePaymentCommand.getBody(), paymentUpdatedBody);

    FmsEvent paymentUpdatedEvent;
    try {
      final String body =
          IApplicationService.getObjectMapper().writeValueAsString(paymentUpdatedBody);

      paymentUpdatedEvent = new FmsEvent(header, body, null);
      log.debug(
          "Mapped to {} event: {}",
          paymentUpdatedEvent.getHeader().getEventName(),
          paymentUpdatedEvent);

    } catch (JsonProcessingException exception) {

      log.error("Exception while mapping publishing event body to String: {}", exception);
      throw exception;
    }

    return paymentUpdatedEvent;
  }

  void publishEventToOutboundTopic(final FmsEvent event) {
    log.info("Inside publishEventToOutboundTopic() method");

    eventPublisher.publishEvent(event);
  }
}
