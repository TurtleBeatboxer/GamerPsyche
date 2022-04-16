import { HttpClient } from '@angular/common/http';
import { Route } from '@angular/compiler/src/core';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { from } from 'rxjs';
import { User, IUser } from '../login/user.model';
import { AuthenticationDTO } from '../list/authenticationDTO.model'

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  user: User;


  constructor(private http: HttpClient, private router: Router) { }

  public authenticate(username: string, password: string){
    this.user = new User(username, password);
    this.http.post<AuthenticationDTO>('http://localhost:8080/user/authenticate', this.user).subscribe(
      x => this.authenticationProcess(x)
    )
  }

  public authenticationProcess(authenticationDTO: AuthenticationDTO): Boolean {
    if(authenticationDTO.success){
      sessionStorage.setItem('username', this.user.username);
      this.router.navigate(['']);
      return true;
    } else if (authenticationDTO.success === false && authenticationDTO.message){
      alert(authenticationDTO.message)
      return false;
    } else {
      return false;
    }
  }

  public isUserLoggedIn(){
    let user = sessionStorage.getItem('username');
    if(user !== null){
      return true;
    } else {
      return false;
    }
  }

  public logOut() {
    sessionStorage.removeItem('username');
  }
}
