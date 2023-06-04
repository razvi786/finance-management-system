import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ApproverLevel } from '../models/ApproverLevel.model';

@Injectable({
  providedIn: 'root',
})
export class ApproverLevelService {
  path: string = environment.apiUrl + '/ems';

  constructor(private http: HttpClient) {}

  getAllApproverLevels(): Observable<ApproverLevel[]> {
    return this.http.get<ApproverLevel[]>(this.path + '/approver-levels');
  }

  getApproverLevelById(id: string): Observable<ApproverLevel> {
    return this.http.get<ApproverLevel>(this.path + '/approver-level/' + id);
  }

  createApproverLevel(approverLevel: ApproverLevel): Observable<ApproverLevel> {
    return this.http.post<ApproverLevel>(
      this.path + '/approver-level',
      approverLevel
    );
  }

  updateApproverLevel(
    id: string,
    approverLevel: ApproverLevel
  ): Observable<ApproverLevel> {
    return this.http.put<ApproverLevel>(
      this.path + '/approver-level/' + id,
      approverLevel
    );
  }

  deleteApproverLevelById(id: string): Observable<String> {
    return this.http.delete<String>(this.path + '/approver-level/' + id);
  }

  getApproverLevelsByProjectId(projectId: string): Observable<ApproverLevel[]> {
    return this.http.get<ApproverLevel[]>(
      this.path + '/project/' + projectId + '/approver-levels'
    );
  }
}
