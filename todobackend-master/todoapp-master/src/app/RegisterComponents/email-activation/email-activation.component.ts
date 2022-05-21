import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-email-activation',
  templateUrl: './email-activation.component.html',
  styleUrls: ['./email-activation.component.css'],
})
export class EmailActivationComponent implements OnInit {
  constructor(private router: Router) {}

  ngOnInit(): void {}

  onClick() {
    this.router.navigate(['/login']);
  }
}
