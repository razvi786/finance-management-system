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
    this.projectService.getAllProjects().subscribe((data) => {
      this.projects = this.sortAscendingOrder(data);
      console.log('Projects : ', this.projects);
    });
    this.dtOptions = {
      pagingType: 'full_numbers',
      pageLength: 10,
    };
  }

  sortAscendingOrder(requests: Project[]): Project[] {
    return requests.sort(
      (project1: Project, project2: Project) =>
        new Date(project2.createdDatetime).getTime() -
        new Date(project1.createdDatetime).getTime()
    );
  }
}
