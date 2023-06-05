import { Injectable } from '@angular/core';
import { User } from '../models/User.model';

@Injectable({
  providedIn: 'root',
})
export class LocalStorageService {
  constructor() {}

  isUserLoggedIn(): boolean {
    let id = localStorage.getItem('userId');
    if (id != null) {
      return true;
    } else {
      return false;
    }
  }

  loginUser(user: User) {
    localStorage.setItem('userId', user.userId.toString());
    localStorage.setItem('roleName', user.role.roleName);
    localStorage.setItem('name', user.name);
    // localStorage.setItem('userData', JSON.stringify(user));
  }

  resetPassword(email: String) {
    localStorage.setItem('email', email.toString());
  }

  logoutUser() {
    localStorage.removeItem('userId');
    localStorage.removeItem('role_name');
    localStorage.removeItem('name');
    // localStorage.removeItem('userData');
  }

  getUserId() {
    return localStorage.getItem('userId');
  }

  getUserName() {
    return localStorage.getItem('name');
  }

  getUserEmail() {
    return localStorage.getItem('email');
  }
}
