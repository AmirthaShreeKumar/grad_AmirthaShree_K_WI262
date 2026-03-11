import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class Student {

  students = [
    {regNo:101, rollNo:1, name:"Arun", standard:"10", school:"ABC School"},
    {regNo:102, rollNo:2, name:"Ravi", standard:"9", school:"XYZ School"}
  ];

  getStudents(){
    return this.students;
  }

  addStudent(student:any){
    this.students.push(student);
  }

  deleteStudent(regNo:number){
    this.students = this.students.filter(s => s.regNo != regNo);
  }

  updateStudent(updated:any){
    let index = this.students.findIndex(s => s.regNo == updated.regNo);
    this.students[index] = updated;
  }

}