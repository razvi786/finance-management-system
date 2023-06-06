import { TestBed } from '@angular/core/testing';

import { ApproverLevelService } from './approver-level.service';

describe('ApproverLevelService', () => {
  let service: ApproverLevelService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ApproverLevelService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
