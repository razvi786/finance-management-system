import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LocalStorageService } from '../services/local-storage.service';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  constructor(
    private userService: UserService,
    private fb: FormBuilder,
    private router: Router,
    private localStorageService: LocalStorageService
  ) {}

  loginForm: FormGroup = new FormGroup({});

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      email: ['', Validators.required],
      password: ['', Validators.required],
    });
  }

  onSubmit() {
    let controls = this.loginForm.controls;

    this.userService
      .getUserByEmailAndPassword(
        controls['email'].value,
        controls['password'].value
      )
      .subscribe((data) => {
        if (data.length > 0) {
          this.localStorageService.loginUser(data[0]);
          this.router.navigate(['/home']);
        } else {
          alert('Invalid Email / Password');
        }
      });
  }
}
