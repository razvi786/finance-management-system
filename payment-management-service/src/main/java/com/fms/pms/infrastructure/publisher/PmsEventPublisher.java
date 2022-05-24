package com.fms.pms.infrastructure.publisher;

import static com.fms.pms.constants.PmsConstants.EVENT_NAME;
import static com.fms.pms.constants.PmsConstants.STRING;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fms.pms.application.IApplicationService;
import com.fms.pms.models.FmsEvent;
import com.fms.pms.models.Header;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PmsEventPublisher {

  @Autowired private AmazonSNS snsClient;

  @Value("${aws.sns.pms-topic-out.arn}")
  private String outboundTopicArn;

  @TransactionalEventListener
  public PublishResult publishEvent(final FmsEvent event) throws JsonProcessingException {

    log.debug("Inside PmsEventPublisher.publishEvent() with event: {}", event);

    try {
      final String message = IApplicationService.getObjectMapper().writeValueAsString(event);
      log.debug("Mapped message to String: {}", message);

      final Header header = event.getHeader();
      final Map<String, MessageAttributeValue> messageAttributes = getMessageAttributes(header);

      final PublishRequest publishRequest =
          new PublishRequest()
              .withMessageAttributes(messageAttributes)
              .withTopicArn(outboundTopicArn)
              .withMessage(message);

      final PublishResult publishResult = snsClient.publish(publishRequest);
      log.info(
          "{} Event is published from {} to {} at {} - Message Id: {}",
          header.getEventName(),
          header.getEventFrom(),
          header.getEventTo(),
          header.getEventDateTime(),
          publishResult.getMessageId());

      return publishResult;

    } catch (JsonProcessingException exception) {
      log.error("Exception while mapping publishing event to String: {}", exception);
      throw exception;
    }
  }

  Map<String, MessageAttributeValue> getMessageAttributes(final Header header) {

    final Map<String, MessageAttributeValue> messageAttributes = new HashMap<>();

    messageAttributes.put(
        EVENT_NAME,
        new MessageAttributeValue().withDataType(STRING).withStringValue(header.getEventName()));

    return messageAttributes;
  }
}
