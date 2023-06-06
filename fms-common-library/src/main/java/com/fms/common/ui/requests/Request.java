
package com.fms.common.ui.requests;

import java.time.OffsetDateTime;

import lombok.Data;

@Data
public class Request {

	int raised_by;

	OffsetDateTime deadline_datetime;

	double amount;

	String description;

	int project_id;

}
