import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Vendor } from 'src/app/models/Vendor.model';
import { VendorService } from 'src/app/services/vendor.service';

@Component({
  selector: 'app-view-vendor',
  templateUrl: './view-vendor.component.html',
  styleUrls: ['./view-vendor.component.css'],
})
export class ViewVendorComponent implements OnInit {
  constructor(
    private activatedRoute: ActivatedRoute,
    private vendorService: VendorService
  ) {}

  vendorId: string = '';

  vendor: Vendor = new Vendor();

  ngOnInit(): void {
    let vendorId = this.activatedRoute.snapshot.paramMap.get('vendor_id');
    console.log('vendorId:', vendorId);
    if (vendorId != null) {
      this.vendorId = vendorId;
      this.vendorService.getVendorById(this.vendorId).subscribe((data) => {
        this.vendor = data;
        console.log('Vendor : ', this.vendor);
      });
    }
  }
}
