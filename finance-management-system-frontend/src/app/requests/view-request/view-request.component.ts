import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Request } from 'src/app/models/Request.model';

@Component({
  selector: 'app-view-request',
  templateUrl: './view-request.component.html',
  styleUrls: ['./view-request.component.css'],
})
export class ViewRequestComponent implements OnInit {
  constructor(private activatedRoute: ActivatedRoute) {}

  requestUuid: string = '';

  request: Request = new Request();

  ngOnInit(): void {
    let requestUuid = this.activatedRoute.snapshot.paramMap.get('request_uuid');
    if (requestUuid != null) {
      this.requestUuid = requestUuid;
    }

    let request = new Request();
    request.request_uuid = '12345';
    request.created_datetime = new Date();
    request.deadline_datetime = new Date();
    request.raised_by_name = 'Anonymous';
    request.status = 'RAISED';
    request.project_name = 'HackFSE';
    request.amount = 1000;
    request.description = 'Request for Frontend Evaluation';

    this.request = request;
  }
}
