import { Component } from '@angular/core';
import { Student } from '../../models/student.model';
import { StudentService } from '../../services/student';
import { Router } from '@angular/router';

import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-add-student',
  templateUrl: './add-student.html',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  styles: [`
    .add-student-container {
      max-width: 600px;
      margin: 20px auto;
      padding: 30px;
      background: white;
      border-radius: 10px;
      box-shadow: 0 4px 20px rgba(0,0,0,0.1);
    }

    .form-header {
      text-align: center;
      margin-bottom: 30px;
    }

    .form-header h2 {
      color: #333;
      margin-bottom: 10px;
    }

    .form-header p {
      color: #666;
      margin: 0;
    }

    .form-group {
      margin-bottom: 20px;
    }

    .form-group label {
      display: block;
      margin-bottom: 5px;
      font-weight: 600;
      color: #333;
    }

    .form-group input, .form-group select {
      width: 100%;
      padding: 12px;
      border: 2px solid #e1e5e9;
      border-radius: 6px;
      font-size: 16px;
      transition: border-color 0.3s;
      box-sizing: border-box;
    }

    .form-group input:focus, .form-group select:focus {
      outline: none;
      border-color: #007bff;
      box-shadow: 0 0 0 3px rgba(0,123,255,0.1);
    }

    .form-row {
      display: grid;
      grid-template-columns: 1fr 1fr;
      gap: 20px;
    }

    .form-row .form-group {
      margin-bottom: 0;
    }

    .button-group {
      display: flex;
      gap: 15px;
      margin-top: 30px;
    }

    .btn {
      padding: 12px 24px;
      border: none;
      border-radius: 6px;
      font-size: 16px;
      font-weight: 600;
      cursor: pointer;
      transition: all 0.3s;
      flex: 1;
    }

    .btn-primary {
      background-color: #28a745;
      color: white;
    }

    .btn-primary:hover {
      background-color: #218838;
      transform: translateY(-1px);
    }

    .btn-secondary {
      background-color: #6c757d;
      color: white;
    }

    .btn-secondary:hover {
      background-color: #5a6268;
      transform: translateY(-1px);
    }

    .btn:disabled {
      opacity: 0.6;
      cursor: not-allowed;
      transform: none;
    }

    .error-message {
      color: #dc3545;
      font-size: 14px;
      margin-top: 5px;
    }

    @media (max-width: 768px) {
      .form-row {
        grid-template-columns: 1fr;
      }

      .button-group {
        flex-direction: column;
      }
    }
  `]
})
export class AddStudentComponent {

student: Student = new Student();

constructor(private studentService: StudentService,
            private router: Router){}

saveStudent(){
  this.studentService.addStudent(this.student).subscribe({
    next: (response) => {
      console.log('Student added:', response);
      alert("Student Added");
      this.router.navigate(['/students']);
    },
    error: (err) => {
      console.error('Add failed:', err);
      alert('Failed to add student. Please try again.');
    }
  });
}

}