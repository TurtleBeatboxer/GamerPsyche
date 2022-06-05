import { TestBed } from '@angular/core/testing';

import { LolUserDataService } from './lolUserData.service';

describe('LolUserDataService', () => {
  let service: LolUserDataService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LolUserDataService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
