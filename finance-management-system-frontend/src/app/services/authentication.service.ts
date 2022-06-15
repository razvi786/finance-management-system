import { Injectable } from '@angular/core';
import { LocalStorageService } from './local-storage.service';

@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {
  constructor(private localStorageService: LocalStorageService) {}

  isAuthenticated(): boolean {
    return this.localStorageService.isUserLoggedIn();
  }
}
