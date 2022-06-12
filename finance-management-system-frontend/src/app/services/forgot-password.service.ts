import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ForgotPasswordService {

  constructor(private http: HttpClient) { }
  rootURL = '/api';
  // verifyUser() {
  //   return this.http.get(this.rootURL + '/forgot-password/');
  // }
}
