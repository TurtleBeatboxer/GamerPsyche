import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import Swal from 'sweetalert2';
import { LoginService } from '../service/login.service';
import { UserDATA } from '../service/userDATA.model';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css'],
})
export class ListComponent implements OnInit {
  public messageFromServer = '';

  constructor(private http: HttpClient, public loginService: LoginService) {}

  ngOnInit(): void {
    if (!this.loginService.userData) {
      const username = sessionStorage.getItem('username');
      this.http
        .get<UserDATA>(`http://localhost:8080/user/${username}`)
        .subscribe((x) => (this.loginService.userData = x));
    }
  }

  loginData() {
    console.log(this.loginService.userData);
  }

}
