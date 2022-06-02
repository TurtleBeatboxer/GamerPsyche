import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { LoginService } from 'src/app/service/login.service';



@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css'],
})
export class ListComponent implements OnInit {
  public messageFromServer = '';

  constructor(private http: HttpClient, public loginService: LoginService) {}

  ngOnInit(): void {
    // if (this.loginService.userData === undefined) {
    //   const username = sessionStorage.getItem('username');
    //   this.http
    //     .get<UserDATA>(`http://localhost:8080/user/${username}`)
    //     .subscribe((x) => {this.loginService.userData = x
    //     console.log(this.loginService.userData)
    //     });
    // }
    this.http.get<WinRateDTO>("http://localhost:8080/user/getWinrate").subscribe(x=> console.log(x))
  }

  loginData() {
    console.log(this.loginService.userData);
  }

}
