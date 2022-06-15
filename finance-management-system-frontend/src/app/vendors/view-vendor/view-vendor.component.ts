import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Vendor } from 'src/app/models/Vendor.model';

@Component({
  selector: 'app-view-vendor',
  templateUrl: './view-vendor.component.html',
  styleUrls: ['./view-vendor.component.css'],
})
export class ViewVendorComponent implements OnInit {
  constructor(private activatedRoute: ActivatedRoute) {}

  vendorId: string = '';

  vendor: Vendor = new Vendor();

  ngOnInit(): void {
    let vendorId = this.activatedRoute.snapshot.paramMap.get('vendor_id');
    if (vendorId != null) {
      this.vendorId = vendorId;
    }

    let vendor = new Vendor();
    vendor.vendor_id = +this.vendorId;
    vendor.vendor_name = 'Mr. Razvi';
    vendor.account_holder_name = 'Razvi';
    vendor.account_number = '1234567890';
    vendor.ifsc_code = 'HYD123';
    vendor.bank_name = 'HDFC Bank';
    vendor.branch = 'Hyderabad';
    vendor.upi_id = 'Razvi123@okhdfc';
    vendor.created_datetime = new Date();

    this.vendor = vendor;
  }
}
