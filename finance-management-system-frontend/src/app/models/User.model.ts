import {Role} from './Role.model';
import { Permission } from './Permission.model';
export class User {
  userId: number = 0;

  name: string = '';
  email: string = '';
  verificationCode: string = '';
  phone: string = '';
  password: string = '';
  createdDatetime: Date = new Date();
  updatedDatetime: Date = new Date();
  concurrencyVersion: number = 0;
  // role: Role = new Role();
  // permissions: Permission[] = new Array;
  roleId: number = 0;
  roleName: string = '';
  permissionId: number = 0;
  permissionName: string = '';

  get Password() { return this.password };
  set Password(password: string) { this.password = password };
}
