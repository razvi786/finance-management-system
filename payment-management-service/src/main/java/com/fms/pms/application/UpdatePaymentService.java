package com.fms.pms.application;

import static com.fms.pms.constants.PmsConstants.UPDATE_PAYMENT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fms.pms.domain.commands.HandleUpdatePaymentCommand;
import com.fms.pms.domain.services.UpdatePaymentDomainService;
import com.fms.pms.events.UpdatePaymentBody;
import com.fms.pms.models.FmsEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UpdatePaymentService implements IApplicationService {

  private static final String EVENT_NAME = UPDATE_PAYMENT;

  @Autowired private UpdatePaymentDomainService updatePaymentDomainService;

  @Override
  public String getServiceIdentifier() {
    return EVENT_NAME;
  }

  @Override
  public void process(final FmsEvent fmsEvent) {

    log.debug("Inside UpdatePaymentService.process() method with Event: {}", fmsEvent);
    try {

      final UpdatePaymentBody commandBody =
          IApplicationService.getObjectMapper()
              .readValue(fmsEvent.getBody(), UpdatePaymentBody.class);

      final HandleUpdatePaymentCommand handleUpdatePaymentCommand =
          HandleUpdatePaymentCommand.builder()
              .header(fmsEvent.getHeader())
              .body(commandBody)
              .errors(fmsEvent.getErrors())
              .build();
      log.debug("HandleUpdatePaymentCommand created: {}", handleUpdatePaymentCommand);

      updatePaymentDomainService.on(handleUpdatePaymentCommand);

    } catch (JsonProcessingException exception) {

      log.error("Exception while mapping eventBody to Command: {}", exception);
    }
  }
}
