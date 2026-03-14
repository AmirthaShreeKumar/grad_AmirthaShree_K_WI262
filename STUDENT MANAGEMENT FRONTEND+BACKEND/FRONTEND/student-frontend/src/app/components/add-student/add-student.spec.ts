import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddStudentComponent } from './add-student';

describe('AddStudent', () => {
  let component: AddStudentComponent;
  let fixture: ComponentFixture<AddStudentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AddStudentComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(AddStudentComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
