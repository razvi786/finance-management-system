import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ProjectService } from 'src/app/services/project.service';

@Component({
  selector: 'app-create-project',
  templateUrl: './create-project.component.html',
  styleUrls: ['./create-project.component.css']
})
export class CreateProjectComponent implements OnInit {
  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private projectService: ProjectService
  ) { }

  createProjectForm: FormGroup = new FormGroup({});

  ngOnInit(): void {
    this.createProjectForm=this.formBuilder.group({
      userId: ['',Validators.required],
      projectName: ['',Validators.required],
      description:['',Validators.required],
      budget:['',Validators.required],
      // listed_in_stock_exchanges:['',Validators.required],
      // sector:['',Validators.required],
      // turnOver:['',Validators.required],
      // briefDescription:['',Validators.required],
      // code:['',Validators.required]
    });
  }

  onSubmit(){
    this.projectService.getProjectById(this.createProjectForm.value).subscribe(data => {
      this.router.navigate(['company-list']);
    });
  }

}
