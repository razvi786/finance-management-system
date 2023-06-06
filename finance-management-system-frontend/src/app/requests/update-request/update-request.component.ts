import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Project } from 'src/app/models/Project.model';
import { Request } from 'src/app/models/Request.model';
import { Vendor } from 'src/app/models/Vendor.model';
import { ProjectService } from 'src/app/services/project.service';
import { RequestService } from 'src/app/services/request.service';
import { VendorService } from 'src/app/services/vendor.service';

@Component({
  selector: 'app-update-request',
  templateUrl: './update-request.component.html',
  styleUrls: ['./update-request.component.css'],
})
export class UpdateRequestComponent implements OnInit {
  projects: Project[] = [];
  vendors: Vendor[] = [];
  selectedProject: Project = new Project();
  selectedVendor: Vendor = new Vendor();
  updateRequestForm: FormGroup = new FormGroup({});
  todaysDate = this.datePipe.transform(new Date(), 'yyyy-MM-dd');
  request: Request = new Request();

  constructor(
    private activatedRoute: ActivatedRoute,
    private projectService: ProjectService,
    private requestService: RequestService,
    private vendorService: VendorService,
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
    this.fetchAllVendors();
    this.fetchCurrentRequest();
    this.createUpdateRequestForm();
  }

  createUpdateRequestForm() {
    this.updateRequestForm = this.fb.group({
      projectId: [this.request.projectId],
      vendorId: [this.request.vendorId],
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

  fetchAllVendors() {
    this.vendorService.getAllVendors().subscribe((data) => {
      this.vendors = data;
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

  updateSelectedVendor() {
    let selectedVendorId = this.updateRequestForm.controls['vendorId'].value;

    this.vendorService.getVendorById(selectedVendorId).subscribe((data) => {
      this.selectedVendor = data;
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
    request.vendorId = this.selectedVendor.vendorId;
    request.vendorName = this.selectedVendor.vendorName;
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
