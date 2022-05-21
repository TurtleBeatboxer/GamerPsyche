import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EmailChangePasswordComponent } from './email-change-password.component';

describe('EmailChangePasswordComponent', () => {
  let component: EmailChangePasswordComponent;
  let fixture: ComponentFixture<EmailChangePasswordComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EmailChangePasswordComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EmailChangePasswordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
