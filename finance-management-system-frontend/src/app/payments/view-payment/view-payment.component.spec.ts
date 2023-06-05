import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewPaymentComponent } from './view-payment.component';

describe('ViewPaymentComponent', () => {
  let component: ViewPaymentComponent;
  let fixture: ComponentFixture<ViewPaymentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewPaymentComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewPaymentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
