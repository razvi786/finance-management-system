package com.fms.pms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackages = "com.fms.pms")
@EnableMongoRepositories(basePackages = "com.fms.pms.repository")
public class PaymentManagementService {

  public static void main(String[] args) {
    SpringApplication.run(PaymentManagementService.class, args);
  }
}
