export class Vendor {
  vendor_id: number = 0;
  vendor_name: string = '';
  account_holder_name: string = '';
  account_number: string = '';
  ifsc_code: string = '';
  bank_name: string = '';
  branch: string = '';
  upi_id: string = '';
  created_datetime: Date = new Date();
  updated_datetime: Date = new Date();
  concurrency_version: number = 0;
}
