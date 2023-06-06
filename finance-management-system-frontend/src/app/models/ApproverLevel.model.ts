import { Project } from './Project.model';
import { User } from './User.model';

export class ApproverLevel {
  approverLevelId: number = 0;

  project: Project = new Project();
  approver: User = new User();
  level: number = 1;

  createdDatetime: Date = new Date();
  updatedDatetime: Date = new Date();
  concurrencyVersion: number = 0;
}
