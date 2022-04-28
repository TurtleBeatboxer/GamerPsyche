import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Validators, FormArray, FormBuilder } from '@angular/forms';
<<<<<<< HEAD

/*
  dobra generalnie pawel to zmienilem
  Address byly czyli street i city
  na lolUsername i lolServer
  z tym ze lolServer jest typu wyliczeniowego to
  chuj wie co szczerze zrob zeby sie nie wysypalo i
  wiedz ze zrobilem zmiany w czyms co musisz przeslac do DTO
  dasz rade
  przeczytassz jave
  jakby cos bylo skrajnie zjebane to powiedz
  naprawie albo sie zrevertuje
  jakby to jeszcze gdzies bylo to ty bedziesz wiedzial
  lepiej zeby juz byla moja czesc zrobiona to nie bedziesz czekal
  albo robil czegos awkward

  pozdrawiam
*/

=======
import { Router } from '@angular/router';
>>>>>>> GP-65FIX
@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent implements OnInit {
  profileForm = this.fb.group({
    firstName: ['', Validators.required],
    lastName: [''],
    login: ['', Validators.required],
    email: ['', Validators.required],
    password: ['', Validators.required],
<<<<<<< HEAD
    address: this.fb.group({
      //jebac tego adresa
      city: ['', Validators.required],
      street: [''],
      //wy pier da laj z tym hujstwem
    })
  });

  constructor(private fb: FormBuilder, private http: HttpClient) { }
=======
    lolData: this.fb.group({
      lolServer: ['', Validators.required],
      lolUsername: ['', Validators.required],
    }),
  });
>>>>>>> GP-65FIX

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router
  ) {}

  ngOnInit(): void {}

  onSubmit(): void {
    this.http
      .post<any>('http://localhost:8080/user/register', this.profileForm.value)
      .subscribe(() => console.log('rejestracja'));
    alert('Zatwierdzono formularz');
    this.router.navigate(['/emailsent']);
  }
}
