package com.fms.rms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackages = "com.fms.rms")
@EnableMongoRepositories(basePackages = "com.fms.rms.repository")
public class RequestManagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RequestManagementServiceApplication.class, args);
	}

}
