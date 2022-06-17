import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { LoginService } from 'src/app/service/login.service';

import { WinRateDTO } from '../../service/winRateDTO.model'
import { MainUserDTO } from '../../service/mainUserDTO.model'
@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css'],
})
export class ListComponent implements OnInit {
  public messageFromServer = '';
  private username = localStorage.getItem("username")

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
    //this.http.get<WinRateDTO>("http://localhost:8080/user/getWinrate").subscribe(x=> console.log(x))

  }

  loginData() {
    console.log(this.loginService.userData);
  }

  onClick(){
   // let user = JSON.parse(localStorage.getItem("user"))

   // console.log(user.lolUsername)
   this.http.get<string>(`http://localhost:8080/user/r4/${sessionStorage.getItem("username")}`).subscribe((x)=> console.log(x))
    //this.http.get("https://europe.api.riotgames.com/lol/match/v5/matches/by-puuid/xcTdEUlOyEXhYbEozGKpp4KdwehAXqEQ9XlY87-vERw_TtK3AF7I7AJ41gAvvZ1FERKlgBnIZFH1Ow/ids?start=0&count=20&api_key=RGAPI-57da1420-e086-4a60-a550-1a6609335bd6"
    //).subscribe((x)=>{
    //  console.log(x)
    //})
    //this.http.get<string>(`http://localhost:8080/user/getOrianna/${sessionStorage.getItem("username")}/420`).subscribe((x)=> console.log(x))
    //this.http.get("https://europe.api.riotgames.com/lol/match/v5/matches/by-puuid/6bpouPxjZf16rmsxOFC6Q9L4ri6_dPRp_VHHVotYqORCicwOnTpKCOjZAbsV15xMzjm1a7wPWDls-g/ids?count=10&queue=420").subscribe((x)=> console.log(x))

  }

}
