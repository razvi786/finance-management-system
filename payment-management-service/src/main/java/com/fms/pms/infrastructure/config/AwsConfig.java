package com.fms.pms.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;

@Configuration
public class AwsConfig {

  @Value("${aws.region}")
  private String region;

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
