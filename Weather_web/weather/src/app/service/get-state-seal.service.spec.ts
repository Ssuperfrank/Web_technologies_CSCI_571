import { TestBed } from '@angular/core/testing';

import { GetStateSealService } from './get-state-seal.service';

describe('GetStateSealService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: GetStateSealService = TestBed.get(GetStateSealService);
    expect(service).toBeTruthy();
  });
});
