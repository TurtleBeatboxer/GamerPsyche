import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GravyTrainComponent } from './gravy-train.component';

describe('GravyTrainComponent', () => {
  let component: GravyTrainComponent;
  let fixture: ComponentFixture<GravyTrainComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GravyTrainComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GravyTrainComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
