export class Vendor {
  vendorId: number = 0;
  vendorName: string = '';
  accountHolderName: string = '';
  accountNumber: string = '';
  ifscCode: string = '';
  bankName: string = '';
  branch: string = '';
  upiId: string = '';
  createdDatetime: Date = new Date();
  updatedDatetime: Date = new Date();
  concurrencyVersion: number = 0;
}
