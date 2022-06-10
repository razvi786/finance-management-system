export class Project{
    project_id: number = 0;
    user_id: number = 0;
    user_name: string = '';
    project_name: string = '';
    description: string = '';
    budget: number = 0;
    created_datetime: Date = new Date();
    updated_datetime: Date = new Date();
    concurrency_version: number = 0;

}