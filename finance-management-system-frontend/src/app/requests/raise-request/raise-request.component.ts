import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Project } from 'src/app/models/Project.model';
import { Request } from 'src/app/models/Request.model';
import { LocalStorageService } from 'src/app/services/local-storage.service';
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
  todaysDate = this.datePipe.transform(new Date(), 'yyyy-MM-dd');

  constructor(
    private projectService: ProjectService,
    private requestService: RequestService,
    private localStorageService: LocalStorageService,
    private fb: FormBuilder,
    private router: Router,
    private datePipe: DatePipe
  ) {}

  ngOnInit(): void {
    this.fetchAllProjects();
    this.raiseRequestForm = this.fb.group({
      projectId: [''],
      remainingBudget: [{ value: '0', disabled: true }],
      amount: ['0'],
      description: [''],
      deadline: [this.todaysDate],
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

      this.requestService
        .getRequestsByProjectId(selectedProjectId)
        .subscribe((data) => {
          let totalBudget: number = this.selectedProject.remainingBudget;
          let budget: number =
            totalBudget - this.raiseRequestForm.controls['amount'].value;
          if (budget < 0) {
            alert('Budget Exceeded, please reduce the amount of request');
            this.raiseRequestForm.controls['amount'].patchValue(totalBudget);
            this.updateRemainingBudget();
          }
          this.raiseRequestForm.controls['remainingBudget'].patchValue(budget);
        });
    });
  }

  onSubmit() {
    console.log('Raise Request', this.raiseRequestForm.value);
    let controls = this.raiseRequestForm.controls;
    // Populate Request Body
    let request: Request = new Request();
    request.requestUuid = uuid.v4();
    request.deadlineDatetime = new Date(controls['deadline'].value);
    request.projectId = this.selectedProject.projectId;
    request.projectName = this.selectedProject.projectName;
    request.amount = controls['amount'].value;
    request.description = controls['description'].value;
    request.status = 'INITIATED';
    //Fetch from Local Storage of Logged in user
    request.raisedBy = Number(this.localStorageService.getUserId());
    request.raisedByName = String(this.localStorageService.getUserName());
    console.log('Saving Request', request);
    this.requestService.createRequest(request).subscribe((data) => {
      console.log('Request Saved');
      this.router.navigate(['/requests']);
    });
  }
}
