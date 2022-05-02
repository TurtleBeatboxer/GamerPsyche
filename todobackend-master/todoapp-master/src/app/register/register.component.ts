import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Validators, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';

interface Server {
  value: string;
}
@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent implements OnInit {
  profileForm = this.fb.group({
    firstName: ['', Validators.required],
    lastName: ['', Validators.required],
    login: ['',Validators.required],
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.pattern(/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/)]],
    lolData: this.fb.group({
      lolServer: ['', Validators.required],
      lolUsername: ['', Validators.required],
    }),
  });

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router
  ) {}

  ngOnInit(): void {}

  onSubmit(): void {
    console.log(this.profileForm.value);
    this.http
      .post<any>('http://localhost:8080/user/register', this.profileForm.value)
      .subscribe(() => console.log('rejestracja'));
    this.profileForm.reset();
    this.router.navigate(['/emailsent']);
  }





  servers: Server[] = [
    { value: 'EUW' },
    { value: 'EUNE' },
    { value: 'NA' },
    { value: 'KR' },
    { value: 'JP' },
    { value: 'TR' },
    { value: 'RU' },
    { value: 'OCE' },
    { value: 'LAN' },
    { value: 'LAS' },
    { value: 'BR' },
    { value: 'PBE' },
  ];
}
