

import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Auth } from '../../services/auth';

@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {

  username=""
  password=""
  role="admin"; // default role

  constructor(private auth:Auth, private router:Router){}

  login(){

    if(this.auth.login(this.username,this.password,this.role)){
      this.router.navigate(['/students'])
    }
    else{
      alert("Invalid credentials or role")
    }

  }

}