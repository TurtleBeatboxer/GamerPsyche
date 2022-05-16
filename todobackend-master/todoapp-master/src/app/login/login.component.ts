import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginService } from '../service/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  public username = '';
  public password = '';
  public invalidLogin = false;
  loginForm: FormGroup;
  constructor(
    public loginService: LoginService,
    public router: Router,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      username: [''],
      password: [''],
    });
  }

  public checkLogin() {
    this.loginService.authenticate(
      this.loginForm.value.username,
      this.loginForm.value.password
    );
  }
}
