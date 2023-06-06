import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { Subject } from 'rxjs';
import { DataTableDirective } from 'angular-datatables';
import { Request } from 'src/app/models/Request.model';
import { RequestService } from 'src/app/services/request.service';
declare var $: any;

@Component({
  selector: 'app-all-requests',
  templateUrl: './all-requests.component.html',
  styleUrls: ['./all-requests.component.css'],
})
export class AllRequestsComponent implements OnInit {
  constructor(private requestService: RequestService) {}

  requests: Request[] = [];

  dtOptions: DataTables.Settings = {};

  ngOnInit(): void {
    this.requestService.getAllRequests().subscribe((data) => {
      this.requests = this.sortAscendingOrder(data);
      console.log('Requests : ', this.requests);
    });
    this.dtOptions = {
      pagingType: 'full_numbers',
      pageLength: 10,
    };
  }

  sortAscendingOrder(requests: Request[]): Request[] {
    return requests.sort(
      (request1: Request, request2: Request) =>
        new Date(request2.createdDatetime).getTime() -
        new Date(request1.createdDatetime).getTime()
    );
  }
}
