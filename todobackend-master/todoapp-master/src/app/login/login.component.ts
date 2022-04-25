import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from '../service/login.service';

//  **        **
//  ***       **
//  ****      **
//  ** **     **
//  **  **    **
//  **   **   **
//  **    **  **
//  **     *****
//  **      **** IGGERS FUCK NIGGERS

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public username = '';
  public password = '';
  public invalidLogin = false;

  constructor(public loginService: LoginService, public router: Router) { }

  ngOnInit(): void {
  }

  public checkLogin() {
    this.loginService.authenticate(this.username, this.password)
  }

}
