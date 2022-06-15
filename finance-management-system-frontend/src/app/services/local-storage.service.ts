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
    localStorage.setItem('userId', user.id.toString());
    localStorage.setItem('role', user.role_name);
    localStorage.setItem('userName', user.name);
    localStorage.setItem('userData', JSON.stringify(user));
  }

  logoutUser() {
    localStorage.removeItem('userId');
    localStorage.removeItem('role');
    localStorage.removeItem('userName');
    localStorage.removeItem('userData');
  }

  getUserId() {
    return localStorage.getItem('userId');
  }

  getUserName() {
    return localStorage.getItem('userName');
  }
}
