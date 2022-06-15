import { Component, OnInit } from '@angular/core';
import { Vendor } from 'src/app/models/Vendor.model';

@Component({
  selector: 'app-all-vendors',
  templateUrl: './all-vendors.component.html',
  styleUrls: ['./all-vendors.component.css'],
})
export class AllVendorsComponent implements OnInit {
  constructor() {}

  vendors: Vendor[] = [];

  dtOptions: DataTables.Settings = {};

  ngOnInit(): void {
    let vendor = new Vendor();
    vendor.vendor_id = 123;
    vendor.vendor_name = 'Mr. Razvi';
    vendor.account_holder_name = 'Razvi';
    vendor.account_number = '1234567890';
    vendor.ifsc_code = 'HYD123';
    vendor.bank_name = 'HDFC Bank';
    vendor.branch = 'Hyderabad';
    vendor.upi_id = 'Razvi123@okhdfc';
    vendor.created_datetime = new Date();
    this.vendors.push(vendor);
    this.vendors.push(vendor);
    this.vendors.push(vendor);
    this.vendors.push(vendor);
    this.vendors.push(vendor);
    this.vendors.push(vendor);
    this.vendors.push(vendor);
    this.vendors.push(vendor);
    this.vendors.push(vendor);
    this.vendors.push(vendor);
    this.vendors.push(vendor);
    this.vendors.push(vendor);
    this.vendors.push(vendor);
    this.vendors.push(vendor);
    this.vendors.push(vendor);

    this.dtOptions = {
      pagingType: 'full_numbers',
      pageLength: 10,
    };
  }
}
