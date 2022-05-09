import { TestBed } from '@angular/core/testing';

import { CustomValidationService } from './customvalidation.service';

describe('ServicecustomValidationService', () => {
  let service: CustomValidationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CustomValidationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
