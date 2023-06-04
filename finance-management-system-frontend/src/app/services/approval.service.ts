import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Approval } from '../models/Approval.model';

@Injectable({
  providedIn: 'root',
})
export class ApprovalService {
  path: string = environment.apiUrl + '/ams';

  constructor(private http: HttpClient) {}

  getAllApprovals(): Observable<Approval[]> {
    return this.http.get<Approval[]>(this.path + 'approvals');
  }

  getApprovalById(id: string): Observable<Approval> {
    return this.http.get<Approval>(this.path + '/approval/' + id);
  }

  createApproval(approval: Approval): Observable<Approval> {
    return this.http.post<Approval>(this.path + '/approval', approval);
  }

  updateApproval(id: string, approval: Approval): Observable<Approval> {
    return this.http.put<Approval>(this.path + '/approval/' + id, approval);
  }

  getApprovalsByRequestUuid(requestUuid: string): Observable<Approval[]> {
    return this.http.get<Approval[]>(
      this.path + '/' + requestUuid + '/approvals'
    );
  }
}
