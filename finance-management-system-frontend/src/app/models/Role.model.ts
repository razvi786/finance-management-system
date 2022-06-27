export class Role{
    roleId: number = 0;
    roleName: string = '';
    createdDatetime: Date = new Date();
  updatedDatetime: Date = new Date();
  concurrencyVersion: number = 0;
}