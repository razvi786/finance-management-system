import { Component, OnInit } from '@angular/core';
import { FormGroup,FormControl, Validators, FormBuilder, FormArray } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from '../models/User.model';
import { ForgotPasswordService } from '../services/forgot-password.service';
import { LocalStorageService } from '../services/local-storage.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent implements OnInit {
  // email: string = '';
  // otp: number = 0;
  // sendResetPasswordEmail() {
  //   console.log('it does nothing',this.email);
  // }

  // email: string = '';
  // otp: number = 0;
  // sendResetPasswordEmail() {
  //   console.log('it does nothing',this.email);
  // }
  forgotPasswordForm = new FormGroup ({ email: new FormControl(), otp: new FormControl()});
   users: User[] | undefined;
   currentUser = new User();
   showOTPfield : boolean = false
  constructor(
    private formBuilder:FormBuilder,
    private router:Router,
     private forgotPasswordService:ForgotPasswordService,
     private localStorageService: LocalStorageService) { }
  
  ngOnInit() {
    this.forgotPasswordForm=this.formBuilder.group({
        email: ['',Validators.required],
        otp:['', Validators.required]
    });
  }

  sendResetPasswordEmail(){
    let email=this.forgotPasswordForm.controls.email.value;
    this.showOTPfield = true;
    this.forgotPasswordService.sendEmail(email).subscribe(u=>{
      this.currentUser = u;
      console.log('User name : ',this.currentUser.name)
    })
    
    }

    validateOTP(){
      let email=this.forgotPasswordForm.controls.email.value;
    this.forgotPasswordService.getUserByEmail(email).subscribe(u=>{
      this.currentUser = u;
      console.log('User : ',this.currentUser);
      console.log('Verification Code : ',this.currentUser.verificationCode);
      let otp=this.forgotPasswordForm.controls.otp.value;
      if(this.currentUser.verificationCode == otp){
        //save email in local storage
        this.localStorageService.resetPassword(this.currentUser.email);
        this.router.navigate(['/reset-password'])
      } else {
        alert('Invalid OTP');
      }
    })
    }
}
