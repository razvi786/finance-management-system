package com.fms.common;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface IApplicationService {

	String getServiceIdentifier();

	void process(BaseEvent event) throws JsonProcessingException;
}
