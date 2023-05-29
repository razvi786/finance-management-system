import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Project } from 'src/app/models/Project.model';
import { User } from 'src/app/models/User.model';
import { LocalStorageService } from 'src/app/services/local-storage.service';
import { ProjectService } from 'src/app/services/project.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-create-project',
  templateUrl: './create-project.component.html',
  styleUrls: ['./create-project.component.css'],
})
export class CreateProjectComponent implements OnInit {
  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private projectService: ProjectService,
    private localStorageService: LocalStorageService,
    private userService: UserService
  ) {}

  createProjectForm: FormGroup = new FormGroup({});
  user: User = new User();

  ngOnInit(): void {
    this.createProjectForm = this.formBuilder.group({
      projectName: ['', Validators.required],
      description: ['', Validators.required],
      budget: ['0', Validators.required],
    });
    this.userService
      .getUserById(String(this.localStorageService.getUserId()))
      .subscribe((user) => {
        this.user = user;
      });
  }

  onSubmit() {
    console.log('Create Project', this.createProjectForm.value);
    let controls = this.createProjectForm.controls;
    // Populate Request Body
    let project: Project = new Project();
    project.projectName = controls['projectName'].value;
    project.description = controls['description'].value;
    project.budget = controls['budget'].value;
    project.user = this.user;
    console.log('Saving Project', project);
    this.projectService.createProject(project).subscribe((data) => {
      console.log('Project Saved');
      this.router.navigate(['/projects']);
    });
  }
}
