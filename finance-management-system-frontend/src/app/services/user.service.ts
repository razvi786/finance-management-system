import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../models/User.model';
import { environment } from './../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  path: string = environment.apiUrl;

  usersPath: string = this.path + '/users';

  constructor(private http: HttpClient) {}

  getUserById(id: string): Observable<User> {
    return this.http.get<User>(this.usersPath + '/' + id);
  }

  getUserByEmailAndPassword(
    email: string,
    password: string
  ): Observable<User[]> {
    return this.http.get<User[]>(
      this.usersPath + '?email=' + email + '&password=' + password
    );
  }

  createUser(user: User): Observable<User> {
    return this.http.post<User>(this.usersPath, user);
  }
}
