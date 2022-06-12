import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-update-project',
  templateUrl: './update-project.component.html',
  styleUrls: ['./update-project.component.css']
})
export class UpdateProjectComponent implements OnInit {

  constructor(private activatedRoute: ActivatedRoute) {}

  projectId: string = '';

  ngOnInit(): void {
    let projectId = this.activatedRoute.snapshot.paramMap.get('project_id');
    if (projectId != null) {
      this.projectId = projectId;
    }
  }

}
