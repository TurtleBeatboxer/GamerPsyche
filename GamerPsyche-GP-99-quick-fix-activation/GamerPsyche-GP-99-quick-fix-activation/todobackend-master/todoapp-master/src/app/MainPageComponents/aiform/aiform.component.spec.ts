import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AIFormComponent } from './aiform.component';

describe('AIFormComponent', () => {
  let component: AIFormComponent;
  let fixture: ComponentFixture<AIFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AIFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AIFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
