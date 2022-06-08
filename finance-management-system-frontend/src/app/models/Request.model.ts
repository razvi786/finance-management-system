export class Request {
  request_uuid: string = '';
  raised_by: number = 0;
  raised_by_name: string = '';
  status: string = '';
  deadline_datetime: Date = new Date();
  created_datetime: Date = new Date();
  updated_datetime: Date = new Date();
  concurrency_version: number = 0;
}