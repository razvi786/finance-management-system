export class Payment {
  paymentUuid: string = '';
  requestUuid: string = '';
  projectId: number = 0;
  projectName: string = '';
  vendorId: number = 0;
  vendorName: string = '';
  userId: number = 0;
  userName: string = '';
  amount: number = 0;
  status: string = '';
  transactionId: string = '';
  createdDatetime: Date = new Date();
  updatedDatetime: Date = new Date();
  concurrencyVersion: number = 0;
}
