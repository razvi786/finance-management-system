import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { Subject } from 'rxjs';
import { DataTableDirective } from 'angular-datatables';
import { Project } from 'src/app/models/Project.model';
import { ProjectService } from 'src/app/services/project.service';
declare var $: any;

@Component({
  selector: 'app-all-projects',
  templateUrl: './all-projects.component.html',
  styleUrls: ['./all-projects.component.css'],
})
export class AllProjectsComponent implements OnInit {
  constructor(private projectService: ProjectService) {}

  projects: Project[] = [];
  dtOptions: DataTables.Settings = {};
  ngOnInit() {
    this.projectService.getAllProjects().subscribe(u=>{
      this.projects = u;
      console.log('Projects : ',this.projects)
    })
    // let project = new Project();
    // project.projectId = 1;
    // project.userId = 2;
    // project.projectName = 'Project';
    // project.description = 'Project Description';
    // project.budget = 12000;
    // project.createdDatetime = new Date();
    // project.name = 'Anonymous';
    // this.projects.push(project);
    // this.projects.push(project)
    // this.projects.push(project);
    // this.projects.push(project);
    // this.projects.push(project);
    // this.projects.push(project);
    // this.projects.push(project);
    // this.projects.push(project);
    // this.projects.push(project);
    // this.projects.push(project);
    // this.projects.push(project);
    // this.projects.push(project);
    // this.projects.push(project);
    // this.projects.push(project);
    // this.projects.push(project);

    this.dtOptions = {
      pagingType: 'full_numbers',
      pageLength: 10,
    };
  }
}
