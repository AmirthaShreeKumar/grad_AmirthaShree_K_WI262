import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { Student } from '../../models/student.model';
import { StudentService } from '../../services/student';

@Component({
  selector: 'app-statistics',
  template: `
    <div class="statistics-container">
      <h2>Student Statistics & Advanced Features</h2>

      <div class="feature-grid">
        <!-- Registration number lookup -->
        <div class="feature-card">
          <h3>Lookup by Registration No</h3>
          <input type="number" [(ngModel)]="regNoSearch" placeholder="RegNo">
          <button (click)="getStudentByRegNo()">Search</button>
          <div *ngIf="searchedStudent" class="results">
            <h4>Student {{searchedStudent.regNo}}</h4>
            <table>
              <tr><th>Name</th><td>{{searchedStudent.name}}</td></tr>
              <tr><th>School</th><td>{{searchedStudent.school}}</td></tr>
              <tr><th>Standard</th><td>{{searchedStudent.standard}}</td></tr>
              <tr><th>Percentage</th><td>{{searchedStudent.percentage}}</td></tr>
              <tr><th>Gender</th><td>{{searchedStudent.gender}}</td></tr>
            </table>
          </div>
        </div>

        <!-- School Filtering -->
        <div class="feature-card">
          <h3>Students by School</h3>
          <input type="text" [(ngModel)]="schoolName" placeholder="Enter school name (e.g., KV, DPS)">
          <div class="button-group">
            <button (click)="getStudentsBySchool()">Get Students</button>
            <button (click)="getSchoolCount()">Get School Count</button>
          </div>

          <div *ngIf="schoolStudents.length > 0" class="results">
            <h4>Students in {{schoolName}}:</h4>
            <table>
              <thead>
                <tr>
                  <th>RegNo</th><th>Name</th><th>Standard</th><th>Percentage</th>
                </tr>
              </thead>
              <tbody>
                <tr *ngFor="let student of schoolStudents">
                  <td>{{student.regNo}}</td>
                  <td>{{student.name}}</td>
                  <td>{{student.standard}}</td>
                  <td>{{student.percentage}}</td>
                </tr>
              </tbody>
            </table>
          </div>

          <div *ngIf="schoolCount > 0" class="count-result">
            <strong>Total students in {{schoolName}}: {{schoolCount}}</strong>
          </div>
        </div>

        <!-- Standard Count -->
        <div class="feature-card">
          <h3>Students by Standard Count</h3>
          <input type="number" [(ngModel)]="standard" min="1" max="12">
          <button (click)="getStandardCount()">Get Count</button>
          <div *ngIf="standardCount >= 0" class="count-result">
            <strong>Students in Standard {{standard}}: {{standardCount}}</strong>
          </div>
        </div>

        <!-- Result Filtering -->
        <div class="feature-card">
          <h3>Students by Result (Pass/Fail)</h3>
          <select [(ngModel)]="passFilter">
            <option [value]="true">Pass (40%+)</option>
            <option [value]="false">Fail (<40%)</option>
          </select>
          <button (click)="getStudentsByResult()">Get Results</button>

          <div *ngIf="resultStudents.length > 0" class="results">
            <h4>{{passFilter ? 'Pass' : 'Fail'}} Students:</h4>
            <table>
              <thead>
                <tr>
                  <th>RegNo</th><th>Name</th><th>Percentage</th><th>Result</th>
                </tr>
              </thead>
              <tbody>
                <tr *ngFor="let student of resultStudents">
                  <td>{{student.regNo}}</td>
                  <td>{{student.name}}</td>
                  <td>{{student.percentage}}</td>
                  <td>{{student.percentage >= 40 ? 'PASS' : 'FAIL'}}</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>

        <!-- Gender & Standard Strength -->
        <div class="feature-card">
          <h3>Strength by Gender & Standard</h3>
          <select [(ngModel)]="gender">
            <option value="MALE">Male</option>
            <option value="FEMALE">Female</option>
          </select>
          <input type="number" [(ngModel)]="standard" min="1" max="12" placeholder="Standard">
          <button (click)="getStrengthByGenderAndStandard()">Get Strength</button>
          <div *ngIf="strengthCount >= 0" class="count-result">
            <strong>{{gender}} students in Standard {{standard}}: {{strengthCount}}</strong>
          </div>
        </div>

        <!-- Partial Update (PATCH) -->
        <div class="feature-card">
          <h3>Partial Update (PATCH)</h3>
          <input type="number" [(ngModel)]="patchRegNo" placeholder="RegNo to update">
          <input type="text" [(ngModel)]="patchUpdates.name" placeholder="New name (optional)">
          <input type="number" [(ngModel)]="patchUpdates.percentage" placeholder="New percentage (optional)">
          <input type="text" [(ngModel)]="patchUpdates.school" placeholder="New school (optional)">
          <button (click)="patchStudent()">Update</button>
          <div *ngIf="patchResult" class="count-result">
            <strong>{{patchResult}}</strong>
          </div>
        </div>
      </div>

      <div class="navigation">
        <a routerLink="/students">Back to Student List</a>
      </div>
    </div>
  `,
  styles: [`
    .statistics-container {
      padding: 20px;
      max-width: 1200px;
      margin: 0 auto;
    }

    .feature-grid {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(400px, 1fr));
      gap: 20px;
      margin: 20px 0;
    }

    .feature-card {
      border: 1px solid #ddd;
      border-radius: 8px;
      padding: 20px;
      background: #f9f9f9;
    }

    .feature-card h3 {
      margin-top: 0;
      color: #333;
    }

    .button-group {
      margin: 10px 0;
    }

    .button-group button {
      margin-right: 10px;
      padding: 8px 16px;
      background: #007bff;
      color: white;
      border: none;
      border-radius: 4px;
      cursor: pointer;
    }

    .button-group button:hover {
      background: #0056b3;
    }

    .results table {
      width: 100%;
      border-collapse: collapse;
      margin-top: 10px;
    }

    .results th, .results td {
      border: 1px solid #ddd;
      padding: 8px;
      text-align: left;
    }

    .results th {
      background-color: #f2f2f2;
    }

    .count-result {
      margin-top: 10px;
      padding: 10px;
      background: #e8f5e8;
      border-radius: 4px;
      font-size: 16px;
    }

    .navigation {
      margin-top: 30px;
      text-align: center;
    }

    .navigation a {
      color: #007bff;
      text-decoration: none;
      font-size: 16px;
    }

    .navigation a:hover {
      text-decoration: underline;
    }

    input, select {
      padding: 8px;
      margin: 5px 0;
      border: 1px solid #ddd;
      border-radius: 4px;
      width: 100%;
      max-width: 200px;
    }

    button {
      padding: 8px 16px;
      background: #28a745;
      color: white;
      border: none;
      border-radius: 4px;
      cursor: pointer;
      margin: 5px 0;
    }

    button:hover {
      background: #218838;
    }
  `],
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule]
})
export class StatisticsComponent implements OnInit {

  // Search by registration number
  regNoSearch: number | null = null;
  searchedStudent: Student | null = null;

  // School filtering
  schoolName: string = '';
  schoolStudents: Student[] = [];
  schoolCount: number = -1;

  // Standard count
  standard: number = 1;
  standardCount: number = -1;

  // Result filtering
  passFilter: boolean = true;
  resultStudents: Student[] = [];

  // Gender & Standard strength
  gender: string = 'MALE';
  strengthCount: number = -1;

  // Partial update (PATCH)
  patchRegNo: number | null = null;
  patchUpdates: Partial<Student> = {};
  patchResult: string = '';

  constructor(private studentService: StudentService) { }

  ngOnInit(): void {
  }

  getStudentByRegNo() {
    if (this.regNoSearch == null) {
      alert('Please enter a registration number');
      return;
    }
    this.studentService.getStudent(this.regNoSearch).subscribe({
      next: (student) => {
        this.searchedStudent = student;
      },
      error: (err) => {
        console.error('Failed to fetch student by regNo:', err);
        alert('Student not found');
        this.searchedStudent = null;
      }
    });
  }

  getStudentsBySchool() {
    if (!this.schoolName.trim()) {
      alert('Please enter a school name');
      return;
    }
    this.studentService.getStudentsBySchool(this.schoolName).subscribe({
      next: (students) => {
        this.schoolStudents = students;
        this.schoolCount = -1; // Reset count
      },
      error: (err) => {
        console.error('Failed to get students by school:', err);
        alert('Failed to load students for this school');
      }
    });
  }

  getSchoolCount() {
    if (!this.schoolName.trim()) {
      alert('Please enter a school name');
      return;
    }
    this.studentService.getSchoolCount(this.schoolName).subscribe({
      next: (count) => {
        this.schoolCount = parseInt(count);
      },
      error: (err) => {
        console.error('Failed to get school count:', err);
        alert('Failed to get school count');
      }
    });
  }

  getStandardCount() {
    console.log('Requesting standard count for', this.standard);
    this.studentService.getStandardCount(this.standard).subscribe({
      next: (countStr) => {
        console.log('Standard count response', countStr);
        const parsed = parseInt(countStr);
        if (isNaN(parsed)) {
          console.warn('Parsed standard count is NaN, defaulting to -1');
          this.standardCount = -1;
        } else {
          this.standardCount = parsed;
        }
      },
      error: (err: any) => {
        console.error('Failed to get standard count:', err);
        if (err.status) {
          alert(`Failed to get standard count (status ${err.status}).`);
        } else {
          alert('Failed to get standard count (see console)');
        }
      }
    });
  }

  getStudentsByResult() {
    this.studentService.getStudentsByResult(this.passFilter).subscribe({
      next: (students) => {
        this.resultStudents = students;
      },
      error: (err) => {
        console.error('Failed to get students by result:', err);
        alert('Failed to load students by result');
      }
    });
  }

 getStrengthByGenderAndStandard() {

  const gender = this.gender.toUpperCase();

  console.log('Requesting strength for', gender, this.standard);

  this.studentService
      .getStrengthByGenderAndStandard(gender, this.standard)
      .subscribe({
        next: (countStr) => {
          console.log('Strength response', countStr);

          const parsed = parseInt(countStr);
          this.strengthCount = isNaN(parsed) ? -1 : parsed;
        },
        error: (err) => {
          console.error('Failed to get strength count:', err);
          alert('Failed to get strength count (see console)');
        }
      });
}

  patchStudent() {
    if (this.patchRegNo == null) {
      alert('Please enter a registration number');
      return;
    }
    // Filter out empty values
    const updates: Partial<Student> = {};
    if (this.patchUpdates.name?.trim()) updates.name = this.patchUpdates.name.trim();
    if (this.patchUpdates.percentage != null) updates.percentage = this.patchUpdates.percentage;
    if (this.patchUpdates.school?.trim()) updates.school = this.patchUpdates.school.trim();

    if (Object.keys(updates).length === 0) {
      alert('Please enter at least one field to update');
      return;
    }

    this.studentService.patchStudent(this.patchRegNo, updates).subscribe({
      next: (response) => {
        this.patchResult = `Student ${this.patchRegNo} updated successfully`;
        // Clear form
        this.patchRegNo = null;
        this.patchUpdates = {};
      },
      error: (err) => {
        console.error('Failed to patch student:', err);
        this.patchResult = 'Failed to update student';
      }
    });
  }

}