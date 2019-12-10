import { TestBed } from '@angular/core/testing';

import { WeatherResultService } from './weather-result.service';

describe('WeatherResultService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: WeatherResultService = TestBed.get(WeatherResultService);
    expect(service).toBeTruthy();
  });
});
