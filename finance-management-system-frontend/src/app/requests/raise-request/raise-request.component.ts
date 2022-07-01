import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Project } from 'src/app/models/Project.model';
import { Request } from 'src/app/models/Request.model';
import { ProjectService } from 'src/app/services/project.service';
import { RequestService } from 'src/app/services/request.service';
import * as uuid from 'uuid';

@Component({
  selector: 'app-raise-request',
  templateUrl: './raise-request.component.html',
  styleUrls: ['./raise-request.component.css'],
})
export class RaiseRequestComponent implements OnInit {
  projects: Project[] = [];
  selectedProject: Project = new Project();
  raiseRequestForm: FormGroup = new FormGroup({});
  todaysDate = new Date();

  constructor(
    private projectService: ProjectService,
    private requestService: RequestService,
    private fb: FormBuilder,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.fetchAllProjects();
    this.raiseRequestForm = this.fb.group({
      projectId: [''],
      remainingBudget: [{ value: '0', disabled: true }],
      amount: ['0'],
      description: [''],
      deadline: [''],
    });
  }

  fetchAllProjects() {
    this.projectService.getAllProjects().subscribe((data) => {
      this.projects = data;
    });
  }

  updateRemainingBudget() {
    let selectedProjectId = this.raiseRequestForm.controls['projectId'].value;

    this.projectService.getProjectById(selectedProjectId).subscribe((data) => {
      this.selectedProject = data;
      this.raiseRequestForm.controls['remainingBudget'].patchValue(
        data.remainingBudget
      );
    });
  }

  onSubmit() {
    console.log('Raise Request', this.raiseRequestForm.value);
    let controls = this.raiseRequestForm.controls;
    // Populate Request Body
    let request: Request = new Request();
    request.id = uuid.v4();
    request.deadline_datetime = new Date(controls['deadline'].value);
    request.project_id = this.selectedProject.projectId;
    request.project_name = this.selectedProject.projectName;
    request.amount = controls['amount'].value;
    request.description = controls['description'].value;
    //Fetch from Local Storage of Logged in user
    request.raised_by = 123;
    request.raised_by_name = 'Syed';
    // Remove Below fields
    request.status = 'RAISED';
    request.created_datetime = new Date();
    request.updated_datetime = new Date();
    request.concurrency_version = 0;
    console.log('Saving Request', request);
    this.requestService.createRequest(request).subscribe((data) => {
      console.log('Request Saved');
      this.router.navigate(['/requests']);
    });
  }
}
