import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Notification } from '../models/Notification.model';

@Injectable({
  providedIn: 'root',
})
export class NotificationService {
  path: string = environment.apiUrl + '/nms';

  constructor(private http: HttpClient) {}

  getNotificationsByUserId(userId: string): Observable<Notification[]> {
    return this.http.get<Notification[]>(this.path + '/notification/' + userId);
  }

  setNotificationAsSeen(notificationUuid: string): Observable<Notification> {
    return this.http.get<Notification>(
      this.path + '/notification/' + notificationUuid + '/seen'
    );
  }

  // Notification Interval
  private setNotificationsInterval = new Subject<any>();

  setNotificationInterval(interval: number) {
    //the component that wants to update something, calls this fn
    this.setNotificationsInterval.next({ interval: interval }); //next() will feed the value in Subject
  }

  getNotificationInterval(): Observable<any> {
    //the receiver component calls this function
    return this.setNotificationsInterval.asObservable(); //it returns as an observable to which the receiver funtion will subscribe
  }
}
