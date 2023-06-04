export class Approval {
  approvalUuid: string = '';

  requestUuid: string = '';
  approverLevelId: number = 0;
  approverLevel: number = 0;
  approverId: number = 0;
  approverName: string = '';

  comments: string = '';
  status: string = '';

  createdDatetime: Date = new Date();
  updatedDatetime: Date = new Date();
  concurrencyVersion: number = 0;
}
