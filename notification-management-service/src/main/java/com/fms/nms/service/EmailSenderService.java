package com.fms.nms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailSenderService {

  private static final String CHAR_SET = "UTF-8";
  private final AmazonSimpleEmailService emailService;

  @Value("${aws.ses.sender.email:}")
  private String sender;

  @Autowired
  public EmailSenderService(AmazonSimpleEmailService emailService) {
    this.emailService = emailService;
  }

  /**
   * This method send email using the aws ses sdk
   *
   * @param email email
   * @param body body
   */
  public void sendEmail(String email, String subject, String body) {
    try {
      // The time for request/response round trip to aws in milliseconds
      int requestTimeout = 3000;
      SendEmailRequest request =
          new SendEmailRequest()
              .withDestination(new Destination().withToAddresses(email))
              .withMessage(
                  new Message()
                      .withBody(
                          new Body().withText(new Content().withCharset(CHAR_SET).withData(body)))
                      .withSubject(new Content().withCharset(CHAR_SET).withData(subject)))
              .withSource(sender)
              .withSdkRequestTimeout(requestTimeout);
      emailService.sendEmail(request);
    } catch (RuntimeException e) {
      log.error("Error occurred sending email to {} ", email, e);
    }
  }
}
