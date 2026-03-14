import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { StudentListComponent } from './components/student-list/student-list';
import { AddStudentComponent } from './components/add-student/add-student';
import { UpdateStudentComponent } from './components/update-student/update-student';
import { StatisticsComponent } from './components/statistics/statistics.component';

const routes: Routes = [

{path:'students',component:StudentListComponent},
{path:'add-student',component:AddStudentComponent},
{path:'update-student/:regNo',component:UpdateStudentComponent},
{path:'statistics',component:StatisticsComponent},
{path:'',redirectTo:'students',pathMatch:'full'}

];

@NgModule({
imports: [RouterModule.forRoot(routes)],
exports: [RouterModule]
})
export class AppRoutingModule { }