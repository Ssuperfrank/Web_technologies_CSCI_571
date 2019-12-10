import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CurrentTagComponent } from './current-tag.component';

describe('CurrentTagComponent', () => {
  let component: CurrentTagComponent;
  let fixture: ComponentFixture<CurrentTagComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CurrentTagComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CurrentTagComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
