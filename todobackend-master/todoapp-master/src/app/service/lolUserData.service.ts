import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LolUserData } from './lolUserData.model';

@Injectable({
  providedIn: 'root'
})
export class LolUserDataService {

  constructor(private http: HttpClient) { }

  getLolUserData(lolUsername: string, lolServer: string){
    this.http
      .get<LolUserData>(
        `http://localhost:8080/user/LOLUserDATA/${lolServer}/${lolUsername}`
      ).subscribe((x)=>{
        localStorage.setItem("lolUserData", JSON.stringify(x))
      })
  }
}
