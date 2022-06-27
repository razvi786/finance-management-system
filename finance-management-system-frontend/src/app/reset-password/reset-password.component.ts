import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from '../models/User.model';
import { LocalStorageService } from '../services/local-storage.service';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent implements OnInit {

  constructor(
    private userService: UserService,
    private formBuilder: FormBuilder,
    private router: Router,
    private localStorageService: LocalStorageService) { }
    currentUser = new User();
    resetPasswordForm = new FormGroup ({ newPassword: new FormControl(), confirmPassword: new FormControl()});
  ngOnInit(): void {
    this.resetPasswordForm=this.formBuilder.group({
      newPassword: ['',Validators.required],
      confirmPassword:['', Validators.required]
  });
  }

  resetPassword(){
    const email = this.localStorageService.getUserEmail();
    console.log(email);
    let newPassword=this.resetPasswordForm.controls.newPassword.value;
    let confirmPassword=this.resetPasswordForm.controls.confirmPassword.value;
    if(newPassword == confirmPassword){
      if(email != null){
        this.userService.getUserByEmail(email).subscribe(u=>{
        this.currentUser = u;
        this.currentUser.password = newPassword;
        console.log(this.currentUser);
        this.userService.updateUser(this.currentUser).subscribe(u=>{
          this.router.navigate(['/login']);
        })
        })
      }
      } else {
        alert('New and confirm password are not matching!!!');
      }
    } 
  }
