import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }
  email: string = '';
  otp: number = 0;
  sendResetPasswordEmail() {
    console.log('it does nothing',this.email);
  }

}
