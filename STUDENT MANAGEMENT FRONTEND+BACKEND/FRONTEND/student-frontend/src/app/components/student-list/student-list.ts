import { Component, OnInit, OnDestroy } from '@angular/core';
import { Student } from '../../models/student.model';
import { StudentService } from '../../services/student';
import { Router, NavigationEnd } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter } from 'rxjs/operators';

import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-student-list',
  templateUrl: './student-list.html',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  styles: [`
    .student-list-container {
      padding: 20px;
      max-width: 1200px;
      margin: 0 auto;
    }

    .actions-bar {
      margin-bottom: 20px;
      display: flex;
      gap: 10px;
    }

    .add-btn, .stats-btn {
      padding: 10px 20px;
      text-decoration: none;
      border-radius: 5px;
      font-weight: bold;
      transition: background-color 0.3s;
    }

    .add-btn {
      background-color: #28a745;
      color: white;
    }

    .add-btn:hover {
      background-color: #218838;
    }

    .stats-btn {
      background-color: #007bff;
      color: white;
    }

    .stats-btn:hover {
      background-color: #0056b3;
    }

    .no-students {
      text-align: center;
      padding: 50px;
      background-color: #f8f9fa;
      border-radius: 10px;
      margin-top: 20px;
    }

    .no-students a {
      color: #007bff;
      text-decoration: none;
    }

    .no-students a:hover {
      text-decoration: underline;
    }

    .table-container {
      overflow-x: auto;
      background: white;
      border-radius: 10px;
      box-shadow: 0 2px 10px rgba(0,0,0,0.1);
    }

    .student-table {
      width: 100%;
      border-collapse: collapse;
      font-size: 14px;
    }

    .student-table th {
      background-color: #f8f9fa;
      padding: 15px;
      text-align: left;
      font-weight: 600;
      color: #333;
      border-bottom: 2px solid #dee2e6;
    }

    .student-table td {
      padding: 12px 15px;
      border-bottom: 1px solid #dee2e6;
    }

    .student-row:hover {
      background-color: #f8f9fa;
    }

    .actions-cell {
      white-space: nowrap;
    }

    .edit-btn, .delete-btn {
      padding: 6px 12px;
      margin-right: 5px;
      border: none;
      border-radius: 4px;
      cursor: pointer;
      font-size: 12px;
      transition: background-color 0.3s;
    }

    .edit-btn {
      background-color: #ffc107;
      color: #212529;
    }

    .edit-btn:hover {
      background-color: #e0a800;
    }

    .delete-btn {
      background-color: #dc3545;
      color: white;
    }

    .delete-btn:hover {
      background-color: #c82333;
    }
  `]
})
export class StudentListComponent implements OnInit, OnDestroy {

  students: Student[] = [];
  private routerSubscription!: Subscription;

  constructor(private studentService: StudentService,
              private router: Router) {}

  ngOnInit(): void {
    this.getStudents();
    this.routerSubscription = this.router.events
      .pipe(filter(event => event instanceof NavigationEnd))
      .subscribe(() => {
        if (this.router.url === '/students') {
          this.getStudents();
        }
      });
  }

  ngOnDestroy(): void {
    if (this.routerSubscription) {
      this.routerSubscription.unsubscribe();
    }
  }

  getStudents(){
    console.log('Fetching students from backend...');
    this.studentService.getStudents().subscribe({
      next: (data: Student[]) => {
        console.log('Students received:', data.length, 'students');
        this.students = data;
      },
      error: (err) => {
        console.error('Failed to fetch students:', err);
        alert('Failed to load students. Please refresh the page.');
      }
    });
  }

  deleteStudent(regNo:number){
    if (confirm('Are you sure you want to delete this student?')) {
      console.log('Starting delete for regNo:', regNo);
      this.studentService.deleteStudent(regNo).subscribe({
        next: (response: string) => {
          console.log('Delete successful, refreshing list...');
          alert("Student Deleted");
          // Force refresh the list
          setTimeout(() => {
            this.getStudents();
          }, 100);
        },
        error: (err: any) => {
          console.error('Delete failed:', err);
          alert('Failed to delete student. Please try again.');
        }
      });
    }
  }

  updateStudent(regNo:number){
    this.router.navigate(['update-student',regNo]);
  }
  getPercentageClass(percentage: number): string {

  if (percentage >= 75) {
    return 'high';
  } 
  
  else if (percentage >= 40) {
    return 'medium';
  } 
  
  else {
    return 'low';
  }

}

}