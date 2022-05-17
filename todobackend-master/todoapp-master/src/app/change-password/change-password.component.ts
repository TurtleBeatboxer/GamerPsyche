import { HttpClient } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, FormGroupDirective, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatAccordion } from '@angular/material/expansion';
import { DialogComponent } from '../dialog/dialog.component';
import { CustomValidationService } from '../service/customvalidation.service';
import { LoginService } from '../service/login.service';
@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit {
  changeForm: FormGroup
  isValid: boolean

  constructor(private fb: FormBuilder, private customValidator: CustomValidationService, public dialog: MatDialog, private http: HttpClient, private userService: LoginService) { }

  ngOnInit(): void {
    this.changeForm = this.fb.group({
      oldPassword: ['', [Validators.required, ]],
      newPassword: ['', [Validators.required, ]],
      newPasswordR: ['', [Validators.required]],
      username: [sessionStorage.getItem('username')],
    },
    {
      validator: this.customValidator.MatchPassword('newPassword', 'newPasswordR')
    }
    )
    this.changeForm.markAsPristine()
  }

  onSubmit(){
     if(this.changeForm.invalid){
      const dialogRef = this.dialog.open(DialogComponent);
      return
     }
     if(this.changeForm.value){
     let data = {oldPassword: this.changeForm.value.oldPassword,
      newPassword:  this.changeForm.value.newPassword,
      newPasswordR: this.changeForm.value.newPasswordR,
      username: sessionStorage.getItem('username')
}
     this.http.post(`http://localhost:8080/user/change-password`, data)
     .subscribe(x => console.log(x))
     //Trzeba zrobić na backendzie cos do tego.
     //mnie nie wkurwiaj



    ChangePasswordComponent.resetForm(this.changeForm)
}
  }

   static resetForm(form: FormGroup) {
    let control: AbstractControl = null;
    form.reset()
    form.markAsUntouched();
    Object.keys(form.controls).forEach((name) => {
      control = form.controls[name];
      control.setErrors(null);
    });
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
