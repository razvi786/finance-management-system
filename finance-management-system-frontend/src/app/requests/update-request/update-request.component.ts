import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Project } from 'src/app/models/Project.model';
import { Request } from 'src/app/models/Request.model';
import { ProjectService } from 'src/app/services/project.service';
import { RequestService } from 'src/app/services/request.service';

@Component({
  selector: 'app-update-request',
  templateUrl: './update-request.component.html',
  styleUrls: ['./update-request.component.css'],
})
export class UpdateRequestComponent implements OnInit {
  projects: Project[] = [];
  selectedProject: Project = new Project();
  updateRequestForm: FormGroup = new FormGroup({});
  todaysDate = new Date();
  request: Request = new Request();

  constructor(
    private activatedRoute: ActivatedRoute,
    private projectService: ProjectService,
    private requestService: RequestService,
    private fb: FormBuilder,
    private router: Router,
    private datepipe: DatePipe
  ) {}

  requestUuid: string = '';

  ngOnInit(): void {
    let requestUuid = this.activatedRoute.snapshot.paramMap.get('request_uuid');
    if (requestUuid != null) {
      this.requestUuid = requestUuid;
    }
    this.fetchAllProjects();
    this.fetchCurrentRequest();
    this.createUpdateRequestForm();
  }

  createUpdateRequestForm() {
    this.updateRequestForm = this.fb.group({
      projectId: [this.request.project_id],
      remainingBudget: [{ disabled: true }],
      amount: [this.request.amount],
      description: [this.request.description],
      deadline: [
        this.datepipe.transform(this.request.deadline_datetime, 'yyyy-MM-dd'),
      ],
    });
    this.updateRemainingBudget();
  }

  fetchAllProjects() {
    this.projectService.getAllProjects().subscribe((data) => {
      this.projects = data;
    });
  }

  fetchCurrentRequest() {
    this.requestService.getRequestById(this.requestUuid).subscribe((data) => {
      this.request = data;
      this.createUpdateRequestForm();
    });
  }

  updateRemainingBudget() {
    let selectedProjectId = this.updateRequestForm.controls['projectId'].value;

    this.projectService.getProjectById(selectedProjectId).subscribe((data) => {
      this.selectedProject = data;
      this.updateRequestForm.controls['remainingBudget'].patchValue(
        data.remainingBudget
      );
    });
  }

  onSubmit() {
    console.log('Raise Request', this.updateRequestForm.value);
    let controls = this.updateRequestForm.controls;
    // Populate Request Body
    let request: Request = new Request();
    request.id = this.request.id;
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
    this.requestService.updateRequest(request.id, request).subscribe((data) => {
      console.log('Request Updated');
      this.router.navigate(['/requests']);
    });
  }
}
