import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Validators, FormArray, FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  profileForm = this.fb.group({
    firstName: ['', Validators.required],
    lastName: [''],
    login: ['', Validators.required],
    email: ['', Validators.required],
    password: ['', Validators.required],
    address: this.fb.group({
      city: ['', Validators.required],
      street: [''],
    })
  });
  
  constructor(private fb: FormBuilder, private http: HttpClient) { }

  ngOnInit(): void {
  }

  onSubmit(): void {
    this.http.post<any>('http://localhost:8080/user/register', this.profileForm.value).subscribe(
      () => console.log('rejestracja')
    )
    alert('Zatwierdzono formularz');
  }

}
