import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MainUserDTO } from 'src/app/service/mainUserDTO.model';
import { LolUserData } from 'src/app/service/lolUserData.model'
@Component({
  selector: 'app-profile-info',
  templateUrl: './profile-info.component.html',
  styleUrls: ['./profile-info.component.css'],
})
export class ProfileInfoComponent implements OnInit {
  data: boolean = false;
  rankedFlex: string;
  rankedSolo: string;
  normalDraft: string;
  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    let userData: MainUserDTO = JSON.parse(localStorage.getItem('user'));
    let lolUsername = userData.lolUsername;
    let lolServer = userData.lolServer;
    this.http
      .get<LolUserData>(`http://localhost:8080/user/LOLUserDATA/${lolServer}/koczokok`)
      .subscribe((x) => {
        console.log(this.data)
        console.log(x)
        this.normalDraft = x.userWinrate.normalDraft
        this.rankedFlex = x.userWinrate.rankedFlex
        this.rankedSolo = x.userWinrate.rankedSolo
        this.data = true;
        console.log(this.data)
      });
  }
}
