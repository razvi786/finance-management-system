import { Permission } from './Permission.model';

export class Role {
  roleId: number = 0;

  roleName: string = '';
  permissions: Permission[] = [];

  createdDatetime: Date = new Date();
  updatedDatetime: Date = new Date();
  concurrencyVersion: number = 0;
}
