import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { LoginService } from '../service/login.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
})
export class ProfileComponent implements OnInit {
  constructor(private UserService: LoginService, private http: HttpClient) {}

  ngOnInit(): void {}
  onClick() {
    this.http
      .get('http://localhost:8080/user/admin1')
      .subscribe((x) => console.log(x));
  }
}
