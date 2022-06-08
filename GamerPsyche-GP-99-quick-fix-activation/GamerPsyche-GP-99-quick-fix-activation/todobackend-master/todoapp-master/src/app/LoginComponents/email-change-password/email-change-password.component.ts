import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  Validators,
} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
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
    private utility: UtilityService,
    private route: ActivatedRoute
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
    let changeId: string = this.route.snapshot.paramMap.get('id')
    console.log(typeof(changeId));
    this.http.post(`http://localhost:8080/user/email/changePassword/${changeId}`, this.changeForm.value.newPasswordR).subscribe(x => console.log(x))
    this.utility.resetForm(this.changeForm);
  }
}
