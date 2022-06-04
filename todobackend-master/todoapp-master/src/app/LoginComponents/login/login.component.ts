import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { DialogComponent } from '../../UtilityComponents/dialog/dialog.component';
import { LoginService } from '../../service/login.service';
import { UtilityService } from '../../service/utility.service';
import { HttpClient } from '@angular/common/http';
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
    private fb: FormBuilder,
    private dialog: MatDialog,
    private utility: UtilityService,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
    });
  }

  public checkLogin() {
    if (!this.loginForm.valid) {
      this.dialog.open(DialogComponent, {
        data: {
          title: 'Niepowodzenie',
          desc: 'Zmiana hasła się nie powiodła, proszę wpisąć poprawne dane',
        },
      });
    } else {
      this.loginService.authenticate(
        this.loginForm.value.username,
        this.loginForm.value.password
      );
    }
    this.utility.resetForm(this.loginForm);
  }

}
