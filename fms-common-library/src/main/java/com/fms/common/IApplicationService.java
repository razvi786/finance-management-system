package com.fms.common;

public interface IApplicationService {

	String getServiceIdentifier();

	void process(BaseEvent event);
}
