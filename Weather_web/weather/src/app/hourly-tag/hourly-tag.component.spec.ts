import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HourlyTagComponent } from './hourly-tag.component';

describe('HourlyTagComponent', () => {
  let component: HourlyTagComponent;
  let fixture: ComponentFixture<HourlyTagComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HourlyTagComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HourlyTagComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
