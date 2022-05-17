package com.fms.nms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackages = "com.fms.nms")
@EnableMongoRepositories(basePackages = "com.fms.nms.repository")
public class NotificationManagementService {

  public static void main(String[] args) {
    SpringApplication.run(NotificationManagementService.class, args);
  }
}
