package com.fms.nms.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SmsSenderService {

  private static final String AWS_SNS_SMS_TYPE = "AWS.SNS.SMS.SMSType";
  private static final String AWS_SNS_SMS_TYPE_VALUE = "Transactional";
  private static final String AWS_SNS_DATA_TYPE = "String";

  private final AmazonSNS snsClient;

  @Autowired
  public SmsSenderService(AmazonSNS snsClient) {
    this.snsClient = snsClient;
  }

  public boolean sendSms(final String phone, final String message) {
    try {
      // The time for request/response round trip to aws in milliseconds
      int requestTimeout = 10000;
      Map<String, MessageAttributeValue> smsAttributes = new HashMap<>();
      smsAttributes.put(
          AWS_SNS_SMS_TYPE,
          new MessageAttributeValue()
              .withStringValue(AWS_SNS_SMS_TYPE_VALUE)
              .withDataType(AWS_SNS_DATA_TYPE));
      final PublishRequest publishRequest =
          new PublishRequest()
              .withMessage(message)
              .withPhoneNumber(phone)
              .withMessageAttributes(smsAttributes)
              .withSdkRequestTimeout(requestTimeout);

      log.debug("Sending SMS to Phone number: {} with message: {}", phone, message);
      final PublishResult publishResult = snsClient.publish(publishRequest);

      log.info("SMS Sent Successfully with MessageId: {}", publishResult.getMessageId());
      return true;

    } catch (Exception exception) {
      log.error("Error occurred sending sms to {}: {} ", phone, exception);
      return false;
    }
  }
}
