package com.fms.nms.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;

@Configuration
public class AwsConfig {

  @Value("${aws.region:eu-west-2}")
  private String region;

  /**
   * Build the AWS ses client
   *
   * @return AmazonSimpleEmailServiceClientBuilder
   */
  @Bean
  public AmazonSimpleEmailService amazonSimpleEmailService() {
    return AmazonSimpleEmailServiceClientBuilder.standard().withRegion(region).build();
  }

  /**
   * Build the aws sns client with default configuration
   *
   * @return AmazonSNSClient
   */
  @Bean
  public AmazonSNS amazonSNS() {
    return AmazonSNSClient.builder().build();
  }
}
