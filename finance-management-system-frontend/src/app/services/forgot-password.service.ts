import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { User } from '../models/User.model';

@Injectable({
  providedIn: 'root',
})
export class ForgotPasswordService {
  httpUrl = environment.apiUrl + 'forgot-password/';
  constructor(private httpClient: HttpClient, private router: Router) {}
  sendEmail(email: String): Observable<User> {
    return this.httpClient.get<User>(this.httpUrl + 'send-email/' + email);
  }
  getUserByEmail(email: String): Observable<User> {
    return this.httpClient.get<User>(this.httpUrl + email);
  }
  // verifyUser() {
  //   return this.http.get(this.rootURL + '/forgot-password/');
  // }
}
