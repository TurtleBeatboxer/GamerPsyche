import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
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
      oldPassword: ['', [Validators.required, Validators.pattern(/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/)]],
      newPassword: ['', [Validators.required, Validators.pattern(/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/)]],
      newPasswordR: ['', [Validators.required, Validators.pattern(/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/)]],
    },
    {
      validator: this.customValidator.MatchPassword('newPassword', 'newPasswordR')
    }
    )
  }

  onSubmit(){
     if(this.changeForm.invalid){
      const dialogRef = this.dialog.open(DialogComponent);
     }
     this.http.post(`http://localhost:8080/user/${this.userService.user.username}`, this.changeForm.value).subscribe((x)=>console.log(x))
  }
}
