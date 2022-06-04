import { HttpClient } from '@angular/common/http';
import { Route } from '@angular/compiler/src/core';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { from } from 'rxjs';
import { User, IUser } from './user.model';
import { AuthenticationDTO } from './authenticationDTO.model';
import { UserDATA } from './userDATA.model';
import { MatDialog } from '@angular/material/dialog';
import { DialogComponent } from '../UtilityComponents/dialog/dialog.component';
import { MainUserDTO } from './mainUserDTO.model';
@Injectable({
  providedIn: 'root',
})
export class LoginService {
  userData: UserDATA;
  user: User;

  constructor(
    private http: HttpClient,
    private router: Router,
    public dialog: MatDialog
  ) {}

  public authenticate(username: string, password: string) {
    this.user = new User(username, password);
    console.log(this.user);
    this.http
      .post<AuthenticationDTO>(
        'http://localhost:8080/user/authenticate',
        this.user
      )
      .subscribe((x) => {
        console.log(x);
        this.authenticationProcess(x);
      });
  }

  public authenticationProcess(authenticationDTO: AuthenticationDTO): Boolean {
    if (authenticationDTO.success) {
      console.log(this.user);
      sessionStorage.setItem('username', this.user.username);
      this.router.navigate(['/main']);
      let username = sessionStorage.getItem('username');
      this.http
        .get<MainUserDTO>(`http://localhost:8080/user/getMainData/${username}`)
        .subscribe((x) => {
          let user: MainUserDTO = {
            firstName: x.firstName,
            lastName: x.lastName,
            email: x.email,
            lolUsername: x.lolUsername,
            lolServer: x.lolServer,
            username: x.username,
          };

          localStorage.setItem('user', JSON.stringify(user));
        });
      return true;
    }
    const dialogRef = this.dialog.open(DialogComponent);
  }

  public isUserLoggedIn() {
    let user = sessionStorage.getItem('username');
    if (user !== null) {
      return true;
    } else {
      return false;
    }
  }

  public logOut() {
    sessionStorage.removeItem('username');
  }
}
