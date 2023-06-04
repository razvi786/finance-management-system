package com.fms.ams.application;

import com.fms.common.BaseEvent;

public interface IApplicationService {

	String getServiceIdentifier();

	void process(BaseEvent event);
}
