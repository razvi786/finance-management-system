import { Role } from './Role.model';
export class User {
  userId: number = 0;

  name: string = '';
  email: string = '';
  password: string = '';
  verificationCode: string = '';
  phone: string = '';
  role: Role = new Role();
  // roleId: number = 0;
  // roleName: string = '';
  // permissionId: number = 0;
  // permissionName: string = '';

  createdDatetime: Date = new Date();
  updatedDatetime: Date = new Date();
  concurrencyVersion: number = 0;

  get Password() {
    return this.password;
  }
  set Password(password: string) {
    this.password = password;
  }
}
