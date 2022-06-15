import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Request } from 'src/app/models/Request.model';
import { RequestService } from 'src/app/services/request.service';

@Component({
  selector: 'app-view-request',
  templateUrl: './view-request.component.html',
  styleUrls: ['./view-request.component.css'],
})
export class ViewRequestComponent implements OnInit {
  constructor(
    private activatedRoute: ActivatedRoute,
    private requestService: RequestService
  ) {}

  requestUuid: string = '';

  request: Request = new Request();

  ngOnInit(): void {
    let requestUuid = this.activatedRoute.snapshot.paramMap.get('request_uuid');
    if (requestUuid != null) {
      this.requestUuid = requestUuid;
    }

    this.requestService.getRequestById(this.requestUuid).subscribe((data) => {
      this.request = data;
    });
  }
}
