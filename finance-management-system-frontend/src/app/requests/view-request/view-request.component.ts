import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { data } from 'jquery';
import { Approval } from 'src/app/models/Approval.model';
import { Request } from 'src/app/models/Request.model';
import { User } from 'src/app/models/User.model';
import { ApprovalService } from 'src/app/services/approval.service';
import { LocalStorageService } from 'src/app/services/local-storage.service';
import { RequestService } from 'src/app/services/request.service';
import { UserService } from 'src/app/services/user.service';

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
    private datePipe: DatePipe,
    private localStorageService: LocalStorageService,
    private userService: UserService,
    private router: Router
  ) {}

  requestUuid: string = '';

  request: Request = new Request();
  user: User = new User();

  approvals: Approval[] = [];
  approvalsCount: number = 0;
  activeApprovalLevel: number = Number.MAX_VALUE;
  rejectedRequest: boolean = false;
  rejectionLevel: number = 0;

  comments: string = '';

  ngOnInit(): void {
    let requestUuid = this.activatedRoute.snapshot.paramMap.get('request_uuid');
    if (requestUuid != null) {
      this.requestUuid = requestUuid;
    }

    this.requestService.getRequestById(this.requestUuid).subscribe((data) => {
      console.log('Request', data);
      this.request = data;
    });

    let userId = this.localStorageService.getUserId();
    this.userService
      .getUserById(userId != null ? userId : '')
      .subscribe((data) => {
        this.user = data;
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

  isEligibleUser(): boolean {
    let currentapproval;
    if (
      this.activeApprovalLevel <= this.approvals.length &&
      !this.rejectedRequest
    ) {
      currentapproval = this.approvals[this.activeApprovalLevel - 1];
      if (currentapproval.approverId == this.user.userId) return true;
      else if (this.user.role.roleName == 'Admin') return true;
      else return false;
    } else {
      return false;
    }
  }

  approveRequest() {
    let currentapproval = this.approvals[this.activeApprovalLevel - 1];
    currentapproval.comments = this.comments;
    currentapproval.approverId = this.user.userId;

    this.approvalService
      .approveRequest(currentapproval.requestUuid, currentapproval)
      .subscribe((approval) => {
        alert('Request approved successfully');
        this.router.navigate(['/requests']);
      });
  }

  rejectRequest() {
    let currentapproval = this.approvals[this.activeApprovalLevel - 1];
    currentapproval.comments = this.comments;
    currentapproval.approverId = this.user.userId;

    this.approvalService
      .rejectRequest(currentapproval.requestUuid, currentapproval)
      .subscribe((approval) => {
        alert('Request rejected successfully');
        this.router.navigate(['/requests']);
      });
  }
}
