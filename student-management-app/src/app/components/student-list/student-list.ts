


import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { Student} from '../../services/student';
import { Auth} from '../../services/auth';

@Component({
  selector: 'app-student-list',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './student-list.html'
})
export class StudentList {

  students:any
  role:any

  constructor(private service:Student, private auth:Auth){

    this.students=this.service.getStudents()
    this.role=this.auth.getRole()

  }

  delete(regNo:number){
    if(confirm('Are you sure you want to delete this student?')){
      this.service.deleteStudent(regNo);
      // refresh local copy so UI updates immediately
      this.students = this.service.getStudents();
    }
  }

}