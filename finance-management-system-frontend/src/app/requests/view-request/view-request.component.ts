import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Approval } from 'src/app/models/Approval.model';
import { Request } from 'src/app/models/Request.model';
import { ApprovalService } from 'src/app/services/approval.service';
import { RequestService } from 'src/app/services/request.service';

@Component({
  selector: 'app-view-request',
  templateUrl: './view-request.component.html',
  styleUrls: ['./view-request.component.css'],
})
export class ViewRequestComponent implements OnInit {
  constructor(
    private activatedRoute: ActivatedRoute,
    private requestService: RequestService,
    private approvalService: ApprovalService,
    private datePipe: DatePipe
  ) {}

  requestUuid: string = '';

  request: Request = new Request();

  approvals: Approval[] = [];
  approvalsCount: number = 0;
  activeApprovalLevel: number = Number.MAX_VALUE;
  rejectedRequest: boolean = false;
  rejectionLevel: number = 0;

  ngOnInit(): void {
    let requestUuid = this.activatedRoute.snapshot.paramMap.get('request_uuid');
    if (requestUuid != null) {
      this.requestUuid = requestUuid;
    }

    this.requestService.getRequestById(this.requestUuid).subscribe((data) => {
      console.log('Request', data);
      this.request = data;
    });

    this.updateApprovals();
  }

  updateApprovals() {
    this.approvalService
      .getApprovalsByRequestUuid(this.requestUuid)
      .subscribe((data) => {
        console.log('Approvals', data);
        this.approvals = data;
        this.approvalsCount = this.approvals.length;
        console.log('Approvals Count', this.approvalsCount);

        for (let i = 0; i < this.approvalsCount; i++) {
          if (this.approvals[i].status == 'REJECTED') {
            this.rejectedRequest = true;
            this.rejectionLevel = i + 1;
            this.activeApprovalLevel = i + 1;
            break;
          }
          if (this.approvals[i].status == 'INITIATED') {
            this.activeApprovalLevel = i + 1;
            break;
          }
        }
        console.log('Active Approval Level', this.activeApprovalLevel);
        console.log('Is Rejected Request', this.rejectedRequest);
        console.log('Rejection Level', this.rejectionLevel);
      });
  }

  getApprovalMetadata(approval: Approval): string {
    let prefix = approval.status == 'APPROVED' ? 'Approved' : 'Rejected';
    let approverName = approval.approverName;
    let approvedTime = this.datePipe.transform(
      approval.updatedDatetime,
      'yyyy-MM-dd'
    );
    let comments = approval.comments;
    let deadline = this.datePipe.transform(
      this.request.deadlineDatetime,
      'yyyy-MM-dd'
    );

    let outcome =
      prefix +
      ' by ' +
      approverName +
      ' on ' +
      this.datePipe.transform(approvedTime, 'yyyy-MM-dd') +
      ' with comments: ' +
      comments;

    let pending =
      'Pending for review by ' + approverName + '. Deadline: ' + deadline;

    let waiting =
      'Waiting for previous approvals. Assignee: ' +
      approverName +
      '  Deadline: ' +
      deadline;

    if (this.rejectedRequest) {
      if (approval.approverLevel <= this.activeApprovalLevel) return outcome;
      else return '';
    } else {
      if (approval.approverLevel < this.activeApprovalLevel) return outcome;
      else if (approval.approverLevel == this.activeApprovalLevel)
        return pending;
      else return waiting;
    }
  }
}
