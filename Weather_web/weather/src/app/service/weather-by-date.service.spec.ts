import { TestBed } from '@angular/core/testing';

import { WeatherByDateService } from './weather-by-date.service';

describe('WeatherByDateService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: WeatherByDateService = TestBed.get(WeatherByDateService);
    expect(service).toBeTruthy();
  });
});
