import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  Validators,
} from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { DialogComponent } from '../../UtilityComponents/dialog/dialog.component';
import { UtilityService } from '../../service/utility.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css'],
})
export class ForgotPasswordComponent implements OnInit {
  forgotForm: FormGroup;
  constructor(
    private fb: FormBuilder,
    public dialog: MatDialog,
    private utility: UtilityService
  ) {}

  ngOnInit(): void {
    this.forgotForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
    });
  }

  onSubmit() {
    //this.http.post('', this.forgotForm.value)
    // TRZA LINK DODAC CHUJJJJJJJ PIZDA I CHUJJJJJJJJJ i od komentowac cnie
    this.utility.resetForm(this.forgotForm);

    this.dialog.open(DialogComponent, {
      data: {
        title: 'Sukces!',
        desc: 'Sukces wiadomość została wysłana na twój adres email',
      },
    });
  }
}
