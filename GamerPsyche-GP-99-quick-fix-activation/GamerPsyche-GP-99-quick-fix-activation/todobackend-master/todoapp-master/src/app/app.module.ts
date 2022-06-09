import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';


import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { LoginComponent } from './LoginComponents/login/login.component';
import { AppRoutingModule } from './app-routing.module';
import { LogoutComponent } from './MainPageComponents/list/logout/logout.component';
import { RegisterComponent } from './RegisterComponents/register/register.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatNativeDateModule } from '@angular/material/core';
import { MatSelectModule } from '@angular/material/select';
import { MatIconModule } from '@angular/material/icon';
import { MatRadioModule } from '@angular/material/radio';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatButtonModule } from '@angular/material/button';
import { EmailActivationComponent } from './RegisterComponents/email-activation/email-activation.component';
import { MatCardModule } from '@angular/material/card';
import { ActivatedComponent } from './RegisterComponents/activated/activated.component';
import { MatMenuModule } from '@angular/material/menu';
import { ProfileComponent } from './MainPageComponents/profile/profile.component';
import { ChangePasswordComponent } from './MainPageComponents/profile/profile-tab/change-password/change-password.component';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatTabsModule } from '@angular/material/tabs';
import { MatDialogModule } from '@angular/material/dialog';
import { DialogComponent } from './UtilityComponents/dialog/dialog.component';
import { ProfileTabComponent } from './MainPageComponents/profile/profile-tab/profile-tab.component';
import { ForgotPasswordComponent } from './LoginComponents/forgot-password/forgot-password.component';
import { EmailChangePasswordComponent } from './LoginComponents/email-change-password/email-change-password.component';
import { ListComponent } from './MainPageComponents/list/list.component';
import { ProfileInfoComponent } from './MainPageComponents/profile/profile-tab/profile-info/profile-info.component';
import { GravyTrainComponent } from './UtilityComponents/gravy-train/gravy-train.component';

@NgModule({
  declarations: [
    AppComponent,
    ListComponent,
    LoginComponent,
    LogoutComponent,
    RegisterComponent,
    EmailActivationComponent,
    ActivatedComponent,
    ProfileComponent,
    ChangePasswordComponent,
    DialogComponent,
    ProfileTabComponent,
    ForgotPasswordComponent,
    EmailChangePasswordComponent,
    ProfileInfoComponent,
    GravyTrainComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    MatFormFieldModule,
    MatNativeDateModule,
    MatInputModule,
    MatSelectModule,
    MatIconModule,
    MatRadioModule,
    MatGridListModule,
    MatButtonModule,
    MatCardModule,
    MatMenuModule,
    MatExpansionModule,
    MatTabsModule,
    MatDialogModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
