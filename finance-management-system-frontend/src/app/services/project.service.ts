import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Project } from '../models/Project.model';
import { environment } from './../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class ProjectService {
  path: string = environment.apiUrl + '/ems';

  projectsPath: string = this.path + '/projects';

  constructor(private http: HttpClient) {}

  getAllProjects(): Observable<Project[]> {
    return this.http.get<Project[]>(this.projectsPath);
  }

  getProjectById(id: string): Observable<Project> {
    return this.http.get<Project>(this.path + '/project/' + id);
  }

  createProject(project: Project): Observable<Project> {
    return this.http.post<Project>(this.path + '/project', project);
  }

  updateProject(id: string, project: Project): Observable<Project> {
    return this.http.put<Project>(this.path + '/project/' + id, project);
  }
}
