import { Component, OnInit } from '@angular/core';
import { Vendor } from 'src/app/models/Vendor.model';
import { VendorService } from 'src/app/services/vendor.service';

@Component({
  selector: 'app-all-vendors',
  templateUrl: './all-vendors.component.html',
  styleUrls: ['./all-vendors.component.css'],
})
export class AllVendorsComponent implements OnInit {
  constructor(private vendorService: VendorService) {}

  vendors: Vendor[] = [];

  dtOptions: DataTables.Settings = {};

  ngOnInit(): void {
    this.vendorService.getAllVendors().subscribe((data) => {
      this.vendors = this.sortAscendingOrder(data);
      console.log('Vendors : ', this.vendors);
    });
    this.dtOptions = {
      pagingType: 'full_numbers',
      pageLength: 10,
    };
  }

  sortAscendingOrder(vendors: Vendor[]): Vendor[] {
    return vendors.sort(
      (vendor1: Vendor, vendor2: Vendor) =>
        new Date(vendor2.createdDatetime).getTime() -
        new Date(vendor1.createdDatetime).getTime()
    );
  }
}
