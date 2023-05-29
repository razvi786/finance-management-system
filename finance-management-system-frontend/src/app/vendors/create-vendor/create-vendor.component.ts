import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Vendor } from 'src/app/models/Vendor.model';
import { VendorService } from 'src/app/services/vendor.service';

@Component({
  selector: 'app-create-vendor',
  templateUrl: './create-vendor.component.html',
  styleUrls: ['./create-vendor.component.css'],
})
export class CreateVendorComponent implements OnInit {
  createVendorForm: FormGroup = new FormGroup({});

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private vendorService: VendorService
  ) {}

  ngOnInit(): void {
    this.createVendorForm = this.formBuilder.group({
      vendorName: ['', Validators.required],
      accountHolderName: ['', Validators.required],
      accountNumber: ['', Validators.required],
      ifscCode: ['', Validators.required],
      bankName: ['', Validators.required],
      branch: [''],
      upiId: [''],
    });
  }

  onSubmit() {
    console.log('Create Vendor', this.createVendorForm.value);
    let controls = this.createVendorForm.controls;
    // Populate Request Body
    let vendor: Vendor = new Vendor();
    vendor.vendorName = controls['vendorName'].value;
    vendor.accountHolderName = controls['accountHolderName'].value;
    vendor.accountNumber = controls['accountNumber'].value;
    vendor.ifscCode = controls['ifscCode'].value;
    vendor.bankName = controls['bankName'].value;
    vendor.branch = controls['branch'].value;
    vendor.upiId = controls['upiId'].value;

    console.log('Saving Vendor', vendor);
    this.vendorService.createVendor(vendor).subscribe((data) => {
      console.log('Vendor Saved');
      this.router.navigate(['/vendors']);
    });
  }
}
