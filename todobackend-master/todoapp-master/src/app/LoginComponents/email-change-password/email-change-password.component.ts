import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  Validators,
} from '@angular/forms';
import { CustomValidationService } from '../../service/customvalidation.service';
import { UtilityService } from '../../service/utility.service';

@Component({
  selector: 'app-email-change-password',
  templateUrl: './email-change-password.component.html',
  styleUrls: ['./email-change-password.component.css'],
})
export class EmailChangePasswordComponent implements OnInit {
  changeForm: FormGroup;
  constructor(
    private fb: FormBuilder,
    private customValidator: CustomValidationService,
    private http: HttpClient,
    private utility: UtilityService
  ) {}

  ngOnInit(): void {
    this.changeForm = this.fb.group(
      {
        newPassword: ['', [Validators.required]],
        newPasswordR: ['', [Validators.required]],
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
    this.http.post('', this.changeForm.value);
    this.utility.resetForm(this.changeForm);
  }
}
