import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { ListComponent } from './list/list.component';
import { LoginComponent } from './login/login.component';
import { AuthGuardService } from './service/auth-guard.service';
import { LogoutComponent } from './logout/logout.component';
import { RegisterComponent } from './register/register.component';
import { EmailActivationComponent } from './email-activation/email-activation.component';
import { ActivatedComponent } from './activated/activated.component';
import { TestComponent } from './test/test.component';

const routes: Routes = [
  { path: '', component: ListComponent, canActivate: [AuthGuardService] },
  { path: 'emailsent', component: EmailActivationComponent },
  { path: 'login', component: LoginComponent },
  { path: 'activated', component: ActivatedComponent},
  { path: 'testkurwa', component: TestComponent},
  {
    path: 'logout',
    component: LogoutComponent,
    canActivate: [AuthGuardService],
  },
  { path: 'register', component: RegisterComponent },
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forRoot(routes), CommonModule],
  exports: [RouterModule],
})
export class AppRoutingModule {}
