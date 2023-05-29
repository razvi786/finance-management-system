import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Vendor } from 'src/app/models/Vendor.model';
import { VendorService } from 'src/app/services/vendor.service';

@Component({
  selector: 'app-update-vendor',
  templateUrl: './update-vendor.component.html',
  styleUrls: ['./update-vendor.component.css'],
})
export class UpdateVendorComponent implements OnInit {
  updateVendorForm: FormGroup = new FormGroup({});
  vendor: Vendor = new Vendor();

  constructor(
    private formBuilder: FormBuilder,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private vendorService: VendorService
  ) {}

  vendorId: string = '';

  ngOnInit(): void {
    let requestId = this.activatedRoute.snapshot.paramMap.get('vendor_id');
    if (requestId != null) {
      this.vendorId = requestId;
    }
    this.fetchCurrentVendor();
    this.createUpdateVendorForm();
  }

  fetchCurrentVendor() {
    this.vendorService.getVendorById(this.vendorId).subscribe((data) => {
      this.vendor = data;
      console.log('Vendor -> ', data);
      this.createUpdateVendorForm();
    });
  }

  createUpdateVendorForm() {
    this.updateVendorForm = this.formBuilder.group({
      vendorName: [this.vendor.vendorName, Validators.required],
      accountHolderName: [this.vendor.accountHolderName, Validators.required],
      accountNumber: [this.vendor.accountHolderName, Validators.required],
      ifscCode: [this.vendor.ifscCode, Validators.required],
      bankName: [this.vendor.bankName, Validators.required],
      branch: [this.vendor.branch],
      upiId: [this.vendor.upiId],
    });
  }

  onSubmit() {
    console.log('Update Vendor', this.updateVendorForm.value);
    let controls = this.updateVendorForm.controls;
    // Populate Request Body
    let vendor: Vendor = new Vendor();
    vendor = this.vendor;
    vendor.vendorName = controls['vendorName'].value;
    vendor.accountHolderName = controls['accountHolderName'].value;
    vendor.accountNumber = controls['accountNumber'].value;
    vendor.ifscCode = controls['ifscCode'].value;
    vendor.bankName = controls['bankName'].value;
    vendor.branch = controls['branch'].value;
    vendor.upiId = controls['upiId'].value;

    console.log('Updating Vendor', vendor);
    this.vendorService.updateVendor(this.vendorId, vendor).subscribe((data) => {
      console.log('Vendor Updated');
      this.router.navigate(['/vendors']);
    });
  }
}
