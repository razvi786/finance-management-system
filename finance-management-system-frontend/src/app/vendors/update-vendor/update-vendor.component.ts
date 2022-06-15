import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-update-vendor',
  templateUrl: './update-vendor.component.html',
  styleUrls: ['./update-vendor.component.css'],
})
export class UpdateVendorComponent implements OnInit {
  constructor(private activatedRoute: ActivatedRoute) {}

  vendorId: string = '';

  ngOnInit(): void {
    let requestId = this.activatedRoute.snapshot.paramMap.get('vendor_id');
    if (requestId != null) {
      this.vendorId = requestId;
    }
  }
}
