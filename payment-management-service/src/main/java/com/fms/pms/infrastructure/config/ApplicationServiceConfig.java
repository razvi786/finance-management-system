package com.fms.pms.infrastructure.config;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fms.pms.application.IApplicationService;

@Configuration
public class ApplicationServiceConfig {

  @Value("${aws.region}")
  private String region;

  List<IApplicationService> applicationServices;

  @Autowired
  public ApplicationServiceConfig(final List<IApplicationService> applicationServices) {
    this.applicationServices = applicationServices;
  }

  @Bean(name = "applicationServiceMap")
  public Map<String, IApplicationService> getApplciationServiceMap() {
    return applicationServices.stream()
        .collect(Collectors.toMap(IApplicationService::getServiceIdentifier, Function.identity()));
  }
}
