export class User {
  id: number = 0;

  name: string = '';
  email: string = '';
  verificationCode: string = '';
  phone: string = '';
  password: string = '';
  created_datetime: Date = new Date();
  updated_datetime: Date = new Date();
  concurrency_version: number = 0;

  role_id: number = 0;
  role_name: string = '';
  permission_id: number = 0;
  permission_name: string = '';
}
