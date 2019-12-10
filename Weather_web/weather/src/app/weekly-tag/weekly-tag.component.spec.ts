import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WeeklyTagComponent } from './weekly-tag.component';

describe('WeeklyTagComponent', () => {
  let component: WeeklyTagComponent;
  let fixture: ComponentFixture<WeeklyTagComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WeeklyTagComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WeeklyTagComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
