import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class Auth {

  role:string="";

  login(username:string,password:string, role:string){

    // username is irrelevant; only role matters.
    if(role === 'admin'){
      // for admin role, password must be admin123
      if(password === 'admin123'){
        this.role = 'admin';
        return true;
      }
      return false;
    }

    if(role === 'staff'){
      // accept any credentials for staff
      this.role = 'staff';
      return true;
    }

    // unknown role
    return false;
  }

  getRole(){
    return this.role;
  }

}