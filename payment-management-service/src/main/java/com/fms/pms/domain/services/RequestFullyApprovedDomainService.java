package com.fms.pms.domain.services;

import static com.fms.pms.constants.PmsConstants.PAYMENT_INITIATED;
import static com.fms.pms.constants.PmsConstants.PAYMENT_MANAGEMENT_SERVICE;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fms.pms.application.IApplicationService;
import com.fms.pms.domain.commands.HandleRequestFullyApprovedCommand;
import com.fms.pms.models.FmsEvent;
import com.fms.pms.models.Header;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RequestFullyApprovedDomainService {

  @Autowired private ApplicationEventPublisher eventPublisher;

  @Transactional
  public void on(final HandleRequestFullyApprovedCommand requestFullyApprovedCommand)
      throws JsonProcessingException {
    log.debug(
        "Inside RequestFullyCompletedDomainService.on() method with Command: {}",
        requestFullyApprovedCommand);

    final FmsEvent paymentInitiatedEvent = mapToPaymentInitiatedEvent(requestFullyApprovedCommand);

    publishEventToOutboundTopic(paymentInitiatedEvent);
  }

  FmsEvent mapToPaymentInitiatedEvent(
      final HandleRequestFullyApprovedCommand requestFullyApprovedCommand)
      throws JsonProcessingException {
    log.info("Inside mapToPaymentInitiatedEvent() method");

    final Header header = requestFullyApprovedCommand.getHeader();
    header.setEventName(PAYMENT_INITIATED);
    header.setEventFrom(PAYMENT_MANAGEMENT_SERVICE);
    header.setEventTo(null);
    header.setEventDateTime(LocalDateTime.now());

    FmsEvent paymentInitiatedEvent;
    try {
      final String body =
          IApplicationService.getObjectMapper()
              .writeValueAsString(requestFullyApprovedCommand.getBody());

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
    log.debug("Inside publishEventToOutboundTopic() method with event: {}", event);

    eventPublisher.publishEvent(event);
  }
}
