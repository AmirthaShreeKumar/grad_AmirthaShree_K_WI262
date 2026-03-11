


import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { Student } from '../../services/student';

@Component({
  selector: 'app-add-student',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './add-student.html'
})
export class AddStudentComponent {

student={
regNo:'',
rollNo:'',
name:'',
standard:'',
school:''
}

constructor(private service:Student, private router:Router){}

add(){

this.service.addStudent(this.student)

this.router.navigate(['/students'])

}

}