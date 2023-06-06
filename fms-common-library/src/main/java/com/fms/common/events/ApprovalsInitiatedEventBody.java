package com.fms.common.events;

import java.util.ArrayList;
import java.util.List;

import com.fms.common.entity.Approval;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApprovalsInitiatedEventBody {

	private List<Approval> approvals = new ArrayList<>();

}
