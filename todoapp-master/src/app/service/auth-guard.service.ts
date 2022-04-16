import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Router, RouterStateSnapshot } from '@angular/router';
import { LoginService } from './login.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService {

  constructor(private router: Router, private authService: LoginService) { }

  public canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot){
    // jesli uzytkownik jest zalogowany
    if(this.authService.isUserLoggedIn()){
      // zwroc true  
      return true;
      } else {
        // jesli uzytkownik nie jest zalogowany
        // przekieruj uzytkownika na sciezkę /login
        this.router.navigate(['login']);
        // zwroc false 
        return false;
      }
  }
}
