package com.fms.pms.application;

import static com.fms.pms.constants.PmsConstants.REQUEST_FULLY_APPROVED;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fms.pms.domain.commands.HandleRequestFullyApprovedCommand;
import com.fms.pms.domain.services.RequestFullyApprovedDomainService;
import com.fms.pms.events.RequestFullyApprovedBody;
import com.fms.pms.models.FmsEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RequestFullyApprovedService implements IApplicationService {

  private static final String EVENT_NAME = REQUEST_FULLY_APPROVED;

  @Autowired private RequestFullyApprovedDomainService requestFullyApprovedDomainService;

  @Override
  public String getServiceIdentifier() {
    return EVENT_NAME;
  }

  @Override
  public void process(final FmsEvent fmsEvent) {

    log.debug("Inside RequestFullyApprovedService.process() method with Event: {}", fmsEvent);
    try {

      final RequestFullyApprovedBody commandBody =
          IApplicationService.getObjectMapper()
              .readValue(fmsEvent.getBody(), RequestFullyApprovedBody.class);

      final HandleRequestFullyApprovedCommand handleRequestFullyApprovedCommand =
          HandleRequestFullyApprovedCommand.builder()
              .header(fmsEvent.getHeader())
              .body(commandBody)
              .errors(fmsEvent.getErrors())
              .build();
      log.debug("HandleRequestFullyApprovedCommand created: {}", handleRequestFullyApprovedCommand);

      requestFullyApprovedDomainService.on(handleRequestFullyApprovedCommand);

    } catch (JsonProcessingException exception) {

      log.error("Exception while mapping eventBody to Command: {}", exception);
    }
  }
}
