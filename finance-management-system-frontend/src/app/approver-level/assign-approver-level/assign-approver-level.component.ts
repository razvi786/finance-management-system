import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { error } from 'jquery';
import { ApproverLevel } from 'src/app/models/ApproverLevel.model';
import { Project } from 'src/app/models/Project.model';
import { User } from 'src/app/models/User.model';
import { ApproverLevelService } from 'src/app/services/approver-level.service';
import { ProjectService } from 'src/app/services/project.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-assign-approver-level',
  templateUrl: './assign-approver-level.component.html',
  styleUrls: ['./assign-approver-level.component.css'],
})
export class AssignApproverLevelComponent implements OnInit {
  constructor(
    private activatedRoute: ActivatedRoute,
    private projectService: ProjectService,
    private userService: UserService,
    private approverLevelService: ApproverLevelService
  ) {}

  projectId: string = '';

  project: Project = new Project();

  // approverLevels: ApproverLevel[] = [];
  approverLevels: ApproverLevel[] = [];

  users: User[] = [];

  ngOnInit(): void {
    let projectId = this.activatedRoute.snapshot.paramMap.get('project_id');
    console.log('projectId:', projectId);
    if (projectId != null) {
      this.projectId = projectId;
      this.refreshApproverLevels();
    }
  }

  refreshApproverLevels() {
    this.projectService.getProjectById(this.projectId).subscribe((data) => {
      this.project = data;
      console.log('Project : ', this.project);
    });
    this.userService.getAllUsers().subscribe((data) => {
      this.users = data;
      console.log('Users : ', this.users);
    });
    this.approverLevelService
      .getApproverLevelsByProjectId(this.projectId)
      .subscribe((data) => {
        this.approverLevels = data != null ? data : [];
        console.log('Approver Levels : ', this.approverLevels);
      });
  }

  addNewApproverLevel() {
    let approverLevel = new ApproverLevel();
    approverLevel.level = this.approverLevels.length + 1;
    this.approverLevels.push(approverLevel);
  }

  updateApprover(selectedApproverLevel: ApproverLevel, userId: string) {
    console.log('Selected Approver Level', selectedApproverLevel);
    console.log('Selected Approver Level Value', userId);
    selectedApproverLevel.approver.userId = +userId;
    if (selectedApproverLevel.approverLevelId != 0) {
      this.approverLevelService
        .updateApproverLevel(
          String(selectedApproverLevel.approverLevelId),
          selectedApproverLevel
        )
        .subscribe(
          (data) => {
            this.refreshApproverLevels();
            alert(
              'Approver Level is Successfully updated to ' + data.approver.name
            );
          },
          (error) => {
            console.log('ERROR', error);
            alert('Some problem in updating Approver');
          }
        );
    } else {
      selectedApproverLevel.project.projectId = +this.projectId;
      selectedApproverLevel.approver.userId = +userId;
      this.approverLevelService
        .createApproverLevel(selectedApproverLevel)
        .subscribe(
          (data) => {
            this.refreshApproverLevels();
            alert(
              'Approver Level is Successfully created and assigned to ' +
                data.approver.name
            );
          },
          (error) => {
            console.log('ERROR', error);
            alert('Some problem in creating Approver Level');
          }
        );
    }
  }

  deleteLatestApproverLevel() {
    let lastIndex = this.approverLevels.length - 1;
    let approverLevelId = this.approverLevels[lastIndex].approverLevelId;
    this.approverLevelService
      .deleteApproverLevelById(String(approverLevelId))
      .subscribe(
        (data) => {
          this.refreshApproverLevels();
          alert('Latest Approver Level is Successfully deleted');
        },
        (error) => {
          console.log('ERROR', error);
          alert('Some problem in deleting Approver Level');
        }
      );
  }
}
