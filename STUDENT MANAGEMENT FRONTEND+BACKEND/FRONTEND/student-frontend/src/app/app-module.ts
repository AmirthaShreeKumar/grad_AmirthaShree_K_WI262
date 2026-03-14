import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing-module';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

import { App } from './app';
import { StudentListComponent } from './components/student-list/student-list';
import { AddStudentComponent } from './components/add-student/add-student';
import { UpdateStudentComponent } from './components/update-student/update-student';
import { StatisticsComponent } from './components/statistics/statistics.component';

@NgModule({
  declarations: [App],
  imports: [BrowserModule, AppRoutingModule, HttpClientModule, FormsModule, RouterModule, StudentListComponent, AddStudentComponent, UpdateStudentComponent, StatisticsComponent],
  bootstrap: [App],
})
export class AppModule {}
