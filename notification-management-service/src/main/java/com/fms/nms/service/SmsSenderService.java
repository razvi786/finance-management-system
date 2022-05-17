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

  /**
   * Send sms using aws sns sdk
   *
   * @param mobileNo mobileNo
   * @param message message
   * @throws Exception
   */
  public void sendSms(String mobileNo, String message) throws Exception {
    try {
      // The time for request/response round trip to aws in milliseconds
      int requestTimeout = 3000;
      Map<String, MessageAttributeValue> smsAttributes = new HashMap<>();
      smsAttributes.put(
          AWS_SNS_SMS_TYPE,
          new MessageAttributeValue()
              .withStringValue(AWS_SNS_SMS_TYPE_VALUE)
              .withDataType(AWS_SNS_DATA_TYPE));

      PublishResult request =
          snsClient.publish(
              new PublishRequest()
                  .withMessage(message)
                  .withPhoneNumber(mobileNo)
                  .withMessageAttributes(smsAttributes)
                  .withSdkRequestTimeout(requestTimeout));
      log.debug(String.valueOf(request));
    } catch (RuntimeException e) {
      log.error("Error occurred sending sms to {} ", mobileNo, e);
      throw new Exception("Error while sending sms please try again later ", e);
    }
  }
}
