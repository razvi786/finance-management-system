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
  todaysDate = this.datePipe.transform(new Date(), 'yyyy-MM-dd');
  request: Request = new Request();

  constructor(
    private activatedRoute: ActivatedRoute,
    private projectService: ProjectService,
    private requestService: RequestService,
    private fb: FormBuilder,
    private router: Router,
    private datePipe: DatePipe
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
      projectId: [this.request.projectId],
      remainingBudget: [{ disabled: true }],
      amount: [this.request.amount],
      description: [this.request.description],
      deadline: [
        this.datePipe.transform(this.request.deadlineDatetime, 'yyyy-MM-dd'),
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
      console.log('Request -> ', data);
      this.createUpdateRequestForm();
    });
  }

  updateRemainingBudget() {
    let selectedProjectId = this.updateRequestForm.controls['projectId'].value;

    this.projectService.getProjectById(selectedProjectId).subscribe((data) => {
      this.selectedProject = data;

      this.requestService
        .getRequestsByProjectId(selectedProjectId)
        .subscribe((data) => {
          let totalBudget: number = this.selectedProject.remainingBudget;
          for (let request of data) {
            if (request.requestUuid == this.request.requestUuid)
              totalBudget += request.amount;
          }
          let budget: number =
            totalBudget - this.updateRequestForm.controls['amount'].value;
          if (budget < 0) {
            alert('Budget Exceeded, please reduce the amount of request');
            this.updateRequestForm.controls['amount'].patchValue(totalBudget);
            this.updateRemainingBudget();
          }
          this.updateRequestForm.controls['remainingBudget'].patchValue(budget);
        });
    });
  }

  onSubmit() {
    console.log('Update Request', this.updateRequestForm.value);
    let controls = this.updateRequestForm.controls;
    // Populate Request Body
    let request: Request = new Request();
    request = this.request;
    request.requestUuid = this.request.requestUuid;
    request.deadlineDatetime = new Date(controls['deadline'].value);
    request.projectId = this.selectedProject.projectId;
    request.projectName = this.selectedProject.projectName;
    request.amount = controls['amount'].value;
    request.description = controls['description'].value;
    console.log('Updating Request', request);
    this.requestService
      .updateRequest(request.requestUuid, request)
      .subscribe((data) => {
        console.log('Request Updated');
        this.router.navigate(['/requests']);
      });
  }
}
