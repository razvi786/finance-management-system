package com.fms.pms.infrastructure.listener;

import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fms.pms.application.IApplicationService;
import com.fms.pms.models.FmsEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PmsEventListener {

  @Resource(name = "applicationServiceMap")
  private Map<String, IApplicationService> applicationServiceMap;

  @JmsListener(destination = "${aws.sqs.pms-queue-in.name}")
  public void onReceive(@Payload final String incomingMessage) {

    log.debug("Received Message: {}", incomingMessage);
    try {

      final FmsEvent event =
          IApplicationService.getObjectMapper().readValue(incomingMessage, FmsEvent.class);
      log.debug("Incoming Event: {}", event);

      final String eventName = event.getHeader().getEventName();
      log.info("Incoming EventName: {}", eventName);

      final IApplicationService applicationService = applicationServiceMap.get(eventName);

      if (Objects.nonNull(applicationService)) {
        applicationService.process(event);
      } else {
        log.error("No Application Service found with eventName: {}", eventName);
      }

    } catch (JsonProcessingException exception) {
      log.error("Exception while mapping incomingMessage to Event: {}", exception);
    }
  }
}
