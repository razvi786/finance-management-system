import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-update-request',
  templateUrl: './update-request.component.html',
  styleUrls: ['./update-request.component.css'],
})
export class UpdateRequestComponent implements OnInit {
  constructor(private activatedRoute: ActivatedRoute) {}

  requestUuid: string = '';

  ngOnInit(): void {
    let requestUuid = this.activatedRoute.snapshot.paramMap.get('request_uuid');
    if (requestUuid != null) {
      this.requestUuid = requestUuid;
    }
  }
}
