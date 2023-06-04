import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Project } from 'src/app/models/Project.model';
import { ProjectService } from 'src/app/services/project.service';

@Component({
  selector: 'app-view-project',
  templateUrl: './view-project.component.html',
  styleUrls: ['./view-project.component.css'],
})
export class ViewProjectComponent implements OnInit {
  constructor(
    private activatedRoute: ActivatedRoute,
    private projectService: ProjectService,
    private router: Router
  ) {}

  projectId: string = '';

  project: Project = new Project();

  ngOnInit(): void {
    let projectId = this.activatedRoute.snapshot.paramMap.get('project_id');
    console.log('projectId:', projectId);
    if (projectId != null) {
      this.projectId = projectId;
      this.projectService.getProjectById(this.projectId).subscribe((u) => {
        this.project = u;
        console.log('Projects : ', this.project);
      });
    }

    let project = new Project();
    project.projectId = 12345;
    project.user.userId = 9876;
    project.user.name = 'User';
    project.projectName = 'Anonymous';
    project.description = 'Project Description';
    project.budget = 1000;
    project.createdDatetime = new Date();

    this.project = project;
  }

  redirectToApproverLevel() {
    let url = '/projects/' + this.projectId + '/approver-level';
    this.router.navigate([url]);
  }
}
