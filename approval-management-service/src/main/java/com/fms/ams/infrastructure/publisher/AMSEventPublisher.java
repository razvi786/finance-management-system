package com.fms.ams.infrastructure.publisher;

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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fms.ams.AMSConstants;
import com.fms.common.BaseEvent;
import com.fms.common.Header;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AMSEventPublisher {

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private AmazonSNS snsClient;

	@Value("${aws.sns.ams-topic-out.arn}")
	private String outboundTopicArn;

	@TransactionalEventListener
	public PublishResult publishEvent(final BaseEvent event) throws JsonProcessingException {

		log.info("Inside AMSEventPublisher.publishEvent() with event: {}", event);

		try {
			final String message = mapper.writeValueAsString(event);
			log.debug("Mapped message to String: {}", message);

			final Header header = event.getHeader();
			final Map<String, MessageAttributeValue> messageAttributes = getMessageAttributes(header);
			log.info("Message attributes added: {}", messageAttributes);

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

		messageAttributes.put(AMSConstants.EVENT_NAME,
				new MessageAttributeValue().withDataType(AMSConstants.STRING).withStringValue(header.getEventName()));

		return messageAttributes;
	}
}
