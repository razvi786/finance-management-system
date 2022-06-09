import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { Subject } from 'rxjs';
import { DataTableDirective } from 'angular-datatables';
import { Request } from 'src/app/models/Request.model';
declare var $: any;

@Component({
  selector: 'app-all-requests',
  templateUrl: './all-requests.component.html',
  styleUrls: ['./all-requests.component.css'],
})
export class AllRequestsComponent implements OnInit {
  constructor() {}

  requests: Request[] = [];

  dtOptions: DataTables.Settings = {};

  ngOnInit(): void {
    let request = new Request();
    request.request_uuid = '12345';
    request.created_datetime = new Date();
    request.deadline_datetime = new Date();
    request.raised_by_name = 'Anonymous';
    request.status = 'RAISED';
    this.requests.push(request);
    this.requests.push(request);
    this.requests.push(request);
    this.requests.push(request);
    this.requests.push(request);
    this.requests.push(request);
    this.requests.push(request);
    this.requests.push(request);
    this.requests.push(request);
    this.requests.push(request);
    this.requests.push(request);
    this.requests.push(request);
    this.requests.push(request);
    this.requests.push(request);
    this.requests.push(request);

    this.dtOptions = {
      pagingType: 'full_numbers',
      pageLength: 10,
    };
  }
}
