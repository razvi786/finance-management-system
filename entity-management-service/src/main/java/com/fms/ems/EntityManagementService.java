package com.fms.ems;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.fms.ems")
public class EntityManagementService {

  public static void main(String[] args) {
    SpringApplication.run(EntityManagementService.class, args);
  }
}
