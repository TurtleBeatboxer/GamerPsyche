import { Component, OnInit } from '@angular/core';

import { LolUserData } from 'src/app/service/lolUserData.model';

@Component({
  selector: 'app-profile-info',
  templateUrl: './profile-info.component.html',
  styleUrls: ['./profile-info.component.css'],
})
export class ProfileInfoComponent implements OnInit {
  rankedFlex: string;
  rankedSolo: string;
  normalDraft: string;

  recentActivity;
  constructor() {}

  ngOnInit(): void {
    let data: LolUserData = JSON.parse(localStorage.getItem('lolUserData'));
    this.normalDraft = data.userWinrate.normalDraft;
    this.rankedSolo = data.userWinrate.rankedSolo;
    this.rankedFlex = data.userWinrate.rankedFlex;
    this.recentActivity = data.activityList;
    console.log(this.recentActivity);
  }
}
