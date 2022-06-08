import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-view-request',
  templateUrl: './view-request.component.html',
  styleUrls: ['./view-request.component.css'],
})
export class ViewRequestComponent implements OnInit {
  constructor(private activatedRoute: ActivatedRoute) {}

  requestUuid: string = '';

  ngOnInit(): void {
    let requestUuid = this.activatedRoute.snapshot.paramMap.get('request_uuid');
    if (requestUuid != null) {
      this.requestUuid = requestUuid;
    }
  }
}
