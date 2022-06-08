import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { LoginService } from 'src/app/service/login.service';
import { UserDATA } from 'src/app/service/userDATA.model';

@Component({
  selector: 'app-profile-tab',
  templateUrl: './profile-tab.component.html',
  styleUrls: ['./profile-tab.component.css']
})
export class ProfileTabComponent implements OnInit {

  constructor(public loginService: LoginService, private http: HttpClient) { }

  ngOnInit(): void {
    if (this.loginService.userData === undefined) {
      const username = sessionStorage.getItem('username');
      this.http
        .get<UserDATA>(`http://localhost:8080/user/${username}`)
        .subscribe((x) => {this.loginService.userData = x
        console.log(this.loginService.userData)
        });
  }
  }
}
