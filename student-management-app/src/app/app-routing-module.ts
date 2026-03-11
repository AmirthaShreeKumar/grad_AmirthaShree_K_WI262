import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { Login } from './components/login/login';
import { StudentList } from './components/student-list/student-list';
import { AddStudentComponent } from './components/add-student/add-student';
import { EditStudent } from './components/edit-student/edit-student';

import { adminGuard } from './guards/admin-guard';

const routes: Routes = [

{path:'',component:Login},

{path:'students',component:StudentList},

{path:'add',component:AddStudentComponent,canActivate:[adminGuard]},

{path:'edit/:regNo',component:EditStudent,canActivate:[adminGuard]}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }