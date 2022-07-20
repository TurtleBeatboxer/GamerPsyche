import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { LoginService } from 'src/app/service/login.service';
import {MatchHistoryDTO } from '../../service/matchHistoryDTO.model'
import { WinRateDTO } from '../../service/winRateDTO.model';
import { MainUserDTO } from '../../service/mainUserDTO.model';
@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css'],
})
export class ListComponent implements OnInit {
  public messageFromServer = '';
  private username = localStorage.getItem('username');
  public flexWinRate: string;
  public soloWinRate: string;
  public normalWinRate: string;
  public matchHistory: MatchHistoryDTO[] = []
  public  mostPlayedChampions: {name: string , value : number}[] = []
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

  normal() {
    this.http
      .get<string>(
        `http://localhost:8080/user/getOrianna/${sessionStorage.getItem(
          'username'
        )}/400`
      )
      .subscribe((x) => {console.log(x)
      this.normalWinRate = x});
  }

  flex() {
    this.http
      .get<string>(
        `http://localhost:8080/user/getOrianna/${sessionStorage.getItem(
          'username'
        )}/440`
      )
      .subscribe((x) => {
        console.log(x);
        this.flexWinRate = x

      });
  }

  solo() {
    this.http
      .get<string>(
        `http://localhost:8080/user/getOrianna/${sessionStorage.getItem(
          'username'
        )}/420`
      )
      .subscribe((x) => {console.log(x)
      this.soloWinRate = x});
  }
  mostPlayedChampion(){
    this.http.get(`http://localhost:8080/user/${sessionStorage.getItem('username')}/getMostPlayedChampions`).subscribe((x)=>{
      console.log(x)
      let array = [];
    for(const key in x){

      array.push({name: key, value: x[key]})
    }
    this.mostPlayedChampions = array;


//       x.forEach((m) => {
//         for(const key in m){
//           this.mostPlayedChampions.push({name: key, value: m[key]})
//         }
//       })
// console.log(this.mostPlayedChampions)
    })
  }

  getMatchHistory(){
    this.http.get<MatchHistoryDTO[]>(`http://localhost:8080/user/${sessionStorage.getItem('username')}/getMatchHistory`).subscribe((x)=> {console.log(x)
    this.matchHistory = x
  })
  }
}
