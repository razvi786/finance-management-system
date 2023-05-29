export class Request {
  requestUuid: string = '';
  raisedBy: number = 0;
  raisedByName: string = '';
  status: string = '';
  deadlineDatetime: Date = new Date();
  amount: number = 0;
  description: string = '';
  projectId: number = 0;
  projectName: string = '';
  createdDatetime: Date = new Date();
  updatedDatetime: Date = new Date();
  concurrencyVersion: number = 0;
}
