import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Request } from '../models/Request.model';
import { environment } from './../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class RequestService {
  path: string = environment.apiUrl;

  requestsPath: string = this.path + '/requests';

  constructor(private http: HttpClient) {}

  getAllRequests(): Observable<Request[]> {
    return this.http.get<Request[]>(this.requestsPath);
  }

  getRequestById(id: string): Observable<Request> {
    return this.http.get<Request>(this.requestsPath + '/' + id);
  }

  createRequest(request: Request): Observable<Request> {
    return this.http.post<Request>(this.requestsPath, request);
  }

  updateRequest(id: string, request: Request): Observable<Request> {
    return this.http.put<Request>(this.requestsPath + '/' + id, request);
  }
}
