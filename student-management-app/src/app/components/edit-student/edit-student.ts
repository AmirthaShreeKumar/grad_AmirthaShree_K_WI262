import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { Student } from '../../services/student';

@Component({
  selector: 'app-edit-student',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './edit-student.html',
  styleUrl: './edit-student.css',
})
export class EditStudent {
  student: any = { regNo: 0, rollNo: 0, name: '', standard: '', school: '' };

  constructor(
    private service: Student,
    private router: Router,
    private route: ActivatedRoute
  ) {
    const reg = this.route.snapshot.paramMap.get('regNo');
    if (reg) {
      const existing = this.service.getStudents().find(s => s.regNo == +reg);
      if (existing) {
        // clone to avoid editing shared object until save
        this.student = { ...existing };
      }
    }
  }

  save() {
    this.service.updateStudent(this.student);
    this.router.navigate(['/students']);
  }
}
