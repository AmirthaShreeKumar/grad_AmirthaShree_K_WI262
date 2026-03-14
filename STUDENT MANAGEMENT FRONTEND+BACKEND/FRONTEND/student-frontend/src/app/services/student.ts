import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Student } from '../models/student.model';

@Injectable({
  providedIn: 'root'
})
export class StudentService {

  private baseUrl = "http://localhost:8080/students";

  constructor(private http: HttpClient) {}

  getStudents(): Observable<Student[]> {
    return this.http.get<Student[]>(this.baseUrl);
  }

  getStudent(regNo:number): Observable<Student> {
    return this.http.get<Student>(`${this.baseUrl}/${regNo}`);
  }

  addStudent(student:Student): Observable<any>{
    return this.http.post(this.baseUrl,student, { responseType: 'text' });
  }

  updateStudent(regNo:number,student:Student):Observable<any>{
    return this.http.put(`${this.baseUrl}/${regNo}`,student, { responseType: 'text' });
  }

  // Advanced filtering and statistics
  getStudentsBySchool(schoolName: string): Observable<Student[]> {
    return this.http.get<Student[]>(`${this.baseUrl}/school?name=${schoolName}`);
  }

  getSchoolCount(schoolName: string): Observable<any> {
    return this.http.get(`${this.baseUrl}/school/count?name=${schoolName}`, { responseType: 'text' });
  }

  getStandardCount(standard: number): Observable<any> {
    // some backends expect `class`, others `standard` – try both or adjust as needed
    const url = `${this.baseUrl}/school/standard/count?standard=${standard}`;
    return this.http.get(url, { responseType: 'text' });
  }

  getStudentsByResult(pass: boolean): Observable<Student[]> {
    return this.http.get<Student[]>(`${this.baseUrl}/result?pass=${pass}`);
  }

  getStrengthByGenderAndStandard(gender: string, standard: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/strength?gender=${gender}&standard=${standard}`, { responseType: 'text' });
  }

  // Partial update using PATCH
  patchStudent(regNo: number, updates: Partial<Student>): Observable<any> {
    return this.http.patch(`${this.baseUrl}/${regNo}`, updates, { responseType: 'text' });
  }

  deleteStudent(regNo:number):Observable<any>{
    return this.http.delete(`${this.baseUrl}/${regNo}`, { responseType: 'text' });
  }
}