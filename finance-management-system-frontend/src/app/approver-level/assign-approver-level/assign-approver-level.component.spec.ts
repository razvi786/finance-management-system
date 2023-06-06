import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AssignApproverLevelComponent } from './assign-approver-level.component';

describe('AssignApproverLevelComponent', () => {
  let component: AssignApproverLevelComponent;
  let fixture: ComponentFixture<AssignApproverLevelComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AssignApproverLevelComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AssignApproverLevelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
