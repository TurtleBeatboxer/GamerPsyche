import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { DialogComponent } from '../../../../UtilityComponents/dialog/dialog.component';
import { CustomValidationService } from '../../../../service/customvalidation.service';
import { UtilityService } from '../../../../service/utility.service';
@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css'],
})
export class ChangePasswordComponent implements OnInit {
  changeForm: FormGroup;
  isValid: boolean;

  constructor(
    private fb: FormBuilder,
    private customValidator: CustomValidationService,
    public dialog: MatDialog,
    private http: HttpClient,
    private utility: UtilityService
  ) {}

  ngOnInit(): void {
    this.changeForm = this.fb.group(
      {
        oldPassword: ['', [Validators.required]],
        newPassword: ['', [Validators.required]],
        newPasswordR: ['', [Validators.required]],
        username: [sessionStorage.getItem('username')],
      },
      {
        validator: this.customValidator.MatchPassword(
          'newPassword',
          'newPasswordR'
        ),
      }
    );
  }

  onSubmit() {
    if (this.changeForm.value) {
      let data = {
        oldPassword: this.changeForm.value.oldPassword,
        newPassword: this.changeForm.value.newPassword,
        newPasswordR: this.changeForm.value.newPasswordR,
        username: sessionStorage.getItem('username'),
      };
      this.http
        .post(`http://localhost:8080/user/change-password`, data)
        .subscribe((x: { success: boolean; message: string }) => {
          console.log(x);
          if (!x.success) {
            if (x.message === 'złe hasło aktualne do konta!') {
              this.dialog.open(DialogComponent, {
                data: {
                  title: 'Niepowodzenie!',
                  desc: 'Nieprawidłowe hasło do konta',
                },
              });
            }
            if (
              x.message === 'nie ma takiego uzytkownika! CHUJ CI W DUPE! KURWA!'
            ) {
              this.dialog.open(DialogComponent, {
                data: {
                  title: 'Niepowodzenie!',
                  desc: 'Wystąpił błąd, spróbuj ponownie',
                },
              });
            }
          }
          if (x.success) {
            this.dialog.open(DialogComponent, {
              data: {
                title: 'Udało się!',
                desc: 'Zmiana hasła zaszła pomyślnie',
              },
            });
          }
        });
      //Trzeba zrobić na backendzie cos do tego.
      //mnie nie wkurwiaj

      this.utility.resetForm(this.changeForm);
    }
  }
}

// prawidowa walidacja
// this.changeForm = this.fb.group({
//   oldPassword: ['', [Validators.required, Validators.pattern(/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/)]],
//   newPassword: ['', [Validators.required, Validators.pattern(/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/)]],
//   newPasswordR: ['', [Validators.required, Validators.pattern(/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/)]],
//   username: [sessionStorage.getItem('username')],
// },
// {
//   validator: this.customValidator.MatchPassword('newPassword', 'newPasswordR')
// }
// )
