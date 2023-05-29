import { User } from './User.model';
export class Project {
  projectId: number = 0;
  user: User = new User();
  projectName: string = '';
  description: string = '';
  budget: number = 0;
  remainingBudget: number = 0;
  createdDatetime: Date = new Date();
  updatedDatetime: Date = new Date();
  concurrencyVersion: number = 0;
}
