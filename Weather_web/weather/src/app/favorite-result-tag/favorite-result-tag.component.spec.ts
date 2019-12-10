import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FavoriteResultTagComponent } from './favorite-result-tag.component';

describe('FavoriteResultTagComponent', () => {
  let component: FavoriteResultTagComponent;
  let fixture: ComponentFixture<FavoriteResultTagComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FavoriteResultTagComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FavoriteResultTagComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
