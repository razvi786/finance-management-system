import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Project } from 'src/app/models/Project.model';

@Component({
  selector: 'app-view-project',
  templateUrl: './view-project.component.html',
  styleUrls: ['./view-project.component.css']
})
export class ViewProjectComponent implements OnInit {

  constructor(private activatedRoute: ActivatedRoute) {}

  projectId: string = '';

  project: Project = new Project();

  ngOnInit(): void {
    let projectId = this.activatedRoute.snapshot.paramMap.get('project_id');
    if (projectId != null) {
      this.projectId = projectId;
    }

    let project = new Project();
    project.project_id = 12345;
    project.user_id = 9876;
    project.user_name = 'User';
    project.project_name = 'Anonymous';
    project.description = 'Project Description';
    project.budget = 1000;
    project.created_datetime = new Date();

    this.project = project;
  }

}
