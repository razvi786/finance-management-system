import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Project } from '../models/Project.model';
import { environment } from './../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class ProjectService {
  path: string = environment.apiUrl;

  projectsPath: string = this.path + '/projects';

  constructor(private http: HttpClient) {}

  getAllProjects(): Observable<Project[]> {
    return this.http.get<Project[]>(this.projectsPath);
  }

  getProjectById(id: number): Observable<Project> {
    return this.http.get<Project>(this.projectsPath + '/' + id);
  }
}
