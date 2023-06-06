import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Project } from 'src/app/models/Project.model';
import { ProjectService } from 'src/app/services/project.service';

@Component({
  selector: 'app-update-project',
  templateUrl: './update-project.component.html',
  styleUrls: ['./update-project.component.css'],
})
export class UpdateProjectComponent implements OnInit {
  updateProjectForm: FormGroup = new FormGroup({});
  project: Project = new Project();

  constructor(
    private activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    private projectService: ProjectService,
    private router: Router
  ) {}

  projectId: string = '';

  ngOnInit(): void {
    let projectId = this.activatedRoute.snapshot.paramMap.get('project_id');
    if (projectId != null) {
      this.projectId = projectId;
    }
    this.fetchCurrentProject();
    this.createUpdateProjectForm();
  }

  fetchCurrentProject() {
    this.projectService.getProjectById(this.projectId).subscribe((data) => {
      this.project = data;
      console.log('Project -> ', data);
      this.createUpdateProjectForm();
    });
  }

  createUpdateProjectForm() {
    this.updateProjectForm = this.fb.group({
      projectName: [this.project.projectName],
      description: [this.project.description],
      budget: [this.project.budget],
    });
  }

  onSubmit() {
    console.log('Update Project', this.updateProjectForm.value);
    let controls = this.updateProjectForm.controls;
    // Populate Request Body
    let project: Project = new Project();
    project = this.project;
    project.projectName = controls['projectName'].value;
    project.description = controls['description'].value;
    project.budget = controls['budget'].value;
    console.log('Updating Project', project);
    this.projectService
      .updateProject(this.projectId, project)
      .subscribe((data) => {
        console.log('Project Updated');
        this.router.navigate(['/projects']);
      });
  }
}
