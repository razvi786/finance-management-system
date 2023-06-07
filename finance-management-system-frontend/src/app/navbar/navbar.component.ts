import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../services/authentication.service';
import { NotificationService } from '../services/notification.service';
import { LocalStorageService } from '../services/local-storage.service';
import { Notification } from '../models/Notification.model';
import { Subscription } from 'rxjs';
import { data } from 'jquery';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
})
export class NavbarComponent implements OnInit {
  constructor(
    public auth: AuthenticationService,
    private notificationService: NotificationService,
    private localStorageService: LocalStorageService
  ) {
    // subscribe to sender component messages
    this.setNotificationsIntervalSubscription = this.notificationService
      .getNotificationInterval()
      .subscribe((data) => {
        //message contains the data sent from service
        if (data.interval != 0) {
          this.fetchAllNotifications();
          this.setNotificationsInterval(data.interval);
        } else {
          this.clearNotificationsInterval();
        }
      });
  }

  userId: string | null = '';
  notifications: Notification[] = [];
  unseenNotifications: Notification[] = [];
  unseenNotificationsCount: number = 4;
  notificationsLimit: number = 10;
  interval: any;

  messageReceived: any;
  private setNotificationsIntervalSubscription: Subscription; //important to create a subscription

  ngOnInit(): void {}

  ngOnDestroy(): void {
    this.clearNotificationsInterval();
  }

  clearNotificationsInterval() {
    if (this.interval) {
      this.notifications = [];
      clearInterval(this.interval);
    }
  }

  setNotificationsInterval(interval: number) {
    this.interval = setInterval(() => {
      this.fetchAllNotifications();
    }, interval);
  }

  fetchAllNotifications() {
    this.userId = this.localStorageService.getUserId();
    if (this.userId != null) {
      this.notificationService
        .getNotificationsByUserId(this.userId)
        .subscribe((data) => {
          this.notifications = this.sortAscendingOrder(data);

          this.unseenNotifications = this.notifications.filter(
            (notification) => notification.seenDatetime == null
          );
          this.unseenNotificationsCount = this.unseenNotifications.length;
        });
    }
  }

  sortAscendingOrder(notifications: Notification[]): Notification[] {
    return notifications.sort(
      (notification1: Notification, notification2: Notification) =>
        new Date(notification2.appSentDatetime).getTime() -
        new Date(notification1.appSentDatetime).getTime()
    );
  }

  markNotificaitonAsSeen(notificationUuid: string) {
    this.notificationService
      .setNotificationAsSeen(notificationUuid)
      .subscribe((data) => {
        this.fetchAllNotifications();
      });
  }
}
