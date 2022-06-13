export class User{
    user_id: number = 0;
    role_id: number = 0;
    name: string = '';
    email: string = '';
    verification_code: string = '';
    phone: string = '';
    password: string = '';
    created_datetime: Date = new Date();
    updated_datetime: Date = new Date();
    concurrency_version: number = 0;
}