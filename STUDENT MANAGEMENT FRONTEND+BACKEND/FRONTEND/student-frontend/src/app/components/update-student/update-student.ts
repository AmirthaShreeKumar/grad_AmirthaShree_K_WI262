import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { Student } from '../../models/student.model';
import { StudentService } from '../../services/student';
import { ActivatedRoute, Router } from '@angular/router';

import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-update-student',
  templateUrl: './update-student.html',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule]
})
export class UpdateStudentComponent implements OnInit {

regNo!: number;
student: Student = new Student();
isLoading = true;

constructor(private studentService: StudentService,
            private route: ActivatedRoute,
            private router: Router,
            private cdr: ChangeDetectorRef){}

ngOnInit(): void {
this.regNo = parseInt(this.route.snapshot.params['regNo'], 10);
console.log('Loading student with regNo:', this.regNo);
this.loadStudent();
}

private loadStudent(): void {
this.isLoading = true;
console.log('Calling getStudent with regNo:', this.regNo);
this.studentService.getStudent(this.regNo).subscribe({
  next: (data: Student) => {
    console.log('Student data received:', data);
    this.student = { ...data };
    this.isLoading = false;
    this.cdr.detectChanges();
  },
  error: (err) => {
    console.error('Failed to load student:', err);
    alert('Failed to load student data: ' + err.message);
    this.isLoading = false;
    this.router.navigate(['/students']);
  }
});
}

updateStudent(){
this.studentService.updateStudent(this.regNo, this.student).subscribe({
  next: (response) => {
    console.log('Update successful:', response);
    alert("Student Updated");
    this.router.navigate(['/students']);
  },
  error: (err) => {
    console.error('Update failed:', err);
    alert('Failed to update student. Please try again.');
  }
});

}

}