package com.fms.common.events;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InitiateApprovalsEventBody {

	private UUID requestUuid;

	private List<InitiateApprovalDetails> approvals = new ArrayList<>();

}
