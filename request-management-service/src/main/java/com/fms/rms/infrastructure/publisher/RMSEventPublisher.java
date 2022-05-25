package com.fms.rms.infrastructure.publisher;

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
import com.fms.rms.application.IApplicationService;
import com.fms.rms.constants.RMSConstants;
import com.fms.rms.models.Header;
import com.fms.rms.models.RMSEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RMSEventPublisher {

	@Autowired
	private AmazonSNS snsClient;

	@Value("${aws.sns.rms-topic-out.arn}")
	private String outboundTopicArn;

	@TransactionalEventListener
	public PublishResult publishEvent(final RMSEvent event) throws JsonProcessingException {

		log.debug("Inside RMSEventPublisher.publishEvent() with event: {}", event);

		try {
			final String message = IApplicationService.getObjectMapper().writeValueAsString(event.getBody());
			log.debug("Mapped message to String: {}", message);

			final Header header = event.getHeader();
			final Map<String, MessageAttributeValue> messageAttributes = getMessageAttributes(header);

			final PublishRequest publishRequest = new PublishRequest().withMessageAttributes(messageAttributes)
					.withTopicArn(outboundTopicArn).withMessage(message);

			final PublishResult publishResult = snsClient.publish(publishRequest);
			log.info("{} Event is published from {} to {} at {} - Message Id: {}", header.getEventName(),
					header.getEventFrom(), header.getEventTo(), header.getEventDateTime(),
					publishResult.getMessageId());

			return publishResult;

		} catch (JsonProcessingException exception) {
			log.error("Exception while mapping publishing event to String: {}", exception);
			throw exception;
		}
	}

	Map<String, MessageAttributeValue> getMessageAttributes(final Header header) {

		final Map<String, MessageAttributeValue> messageAttributes = new HashMap<>();

		messageAttributes.put(RMSConstants.EVENT_NAME,
				new MessageAttributeValue().withDataType(RMSConstants.STRING).withStringValue(header.getEventName()));

		return messageAttributes;
	}
}
