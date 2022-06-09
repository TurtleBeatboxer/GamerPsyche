import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { ListComponent } from './MainPageComponents/list/list.component';
import { LoginComponent } from './LoginComponents/login/login.component';
import { AuthGuardService } from './service/auth-guard.service';
import { LogoutComponent } from './MainPageComponents/list/logout/logout.component';
import { RegisterComponent } from './RegisterComponents/register/register.component';
import { EmailActivationComponent } from './RegisterComponents/email-activation/email-activation.component';
import { ActivatedComponent } from './RegisterComponents/activated/activated.component';
import { ProfileComponent } from './MainPageComponents/profile/profile.component';
import { ForgotPasswordComponent } from './LoginComponents/forgot-password/forgot-password.component';
import { EmailChangePasswordComponent } from './LoginComponents/email-change-password/email-change-password.component';


const routes: Routes = [
  { path: 'main', component: ListComponent, canActivate: [AuthGuardService] },
  { path: 'emailsent', component: EmailActivationComponent },
  { path: 'login', component: LoginComponent },
  { path: 'activated', component: ActivatedComponent },
  { path: 'profil', component: ProfileComponent},
  {
    path: 'logout',
    component: LogoutComponent,
    canActivate: [AuthGuardService],
  },
  { path: 'register', component: RegisterComponent },
  { path: 'forgotpassword', component: ForgotPasswordComponent},
  { path: `changePassword/:id`, component: EmailChangePasswordComponent},

  { path: '**', redirectTo: '/login' }
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forRoot(routes), CommonModule],
  exports: [RouterModule],
})
export class AppRoutingModule {}
