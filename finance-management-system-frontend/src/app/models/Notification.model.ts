export class Notification {
  notificationUuid: string = '';
  notificationType: string = '';
  message: string = '';
  triggeredBy: number = 0;
  triggeredTo: number = 0;
  seenDatetime: Date | undefined;
  appSentDatetime: Date = new Date();
  emailSentDatetime: Date | undefined;
  smsSentDatetime: Date | undefined;
  concurrencyVersion: number = 0;
}
