import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SpecificRecordComponent } from './specific-record.component';

describe('SpecificRecordComponent', () => {
  let component: SpecificRecordComponent;
  let fixture: ComponentFixture<SpecificRecordComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SpecificRecordComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SpecificRecordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
