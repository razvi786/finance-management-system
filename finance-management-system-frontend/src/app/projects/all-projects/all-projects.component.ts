import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { Subject } from 'rxjs';
import { DataTableDirective } from 'angular-datatables';
import { Project } from 'src/app/models/Project.model';
declare var $: any;

@Component({
  selector: 'app-all-projects',
  templateUrl: './all-projects.component.html',
  styleUrls: ['./all-projects.component.css'],
})
export class AllProjectsComponent implements OnInit {
  constructor() {}

  projects: Project[] = [];
  dtOptions: DataTables.Settings = {};
  ngOnInit(): void {
    let project = new Project();
    project.id = 1;
    project.user_id = 2;
    project.project_name = 'Project';
    project.description = 'Project Description';
    project.budget = 12000;
    project.created_datetime = new Date();
    project.user_name = 'Anonymous';
    this.projects.push(project);
    this.projects.push(project);
    this.projects.push(project);
    this.projects.push(project);
    this.projects.push(project);
    this.projects.push(project);
    this.projects.push(project);
    this.projects.push(project);
    this.projects.push(project);
    this.projects.push(project);
    this.projects.push(project);
    this.projects.push(project);
    this.projects.push(project);
    this.projects.push(project);
    this.projects.push(project);

    this.dtOptions = {
      pagingType: 'full_numbers',
      pageLength: 10,
    };
  }
}
