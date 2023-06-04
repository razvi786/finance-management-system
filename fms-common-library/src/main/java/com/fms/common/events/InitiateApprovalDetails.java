package com.fms.common.events;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InitiateApprovalDetails {

	private Integer approverLevelId;

	private Integer approverId;

}
