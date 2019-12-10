import { TestBed } from '@angular/core/testing';

import { FavoriteCitiesService } from './favorite-cities.service';

describe('FavoriteCitiesService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: FavoriteCitiesService = TestBed.get(FavoriteCitiesService);
    expect(service).toBeTruthy();
  });
});
