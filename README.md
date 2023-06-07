# Finance Maanagement System

Project Document:
https://docs.google.com/document/d/1Z4_kSpuOYEDCl4L2ChVSDDzgs-9gdvB5Thikd9ifLxY

Database Design:
https://docs.google.com/document/d/14e5gAoa-3U7Aa53yY3iOqQ9UIkpsmGsKKylFti7gUKw

Event Definitions:
https://docs.google.com/document/d/1raMWo0s9U4Vj5Kh0EMmdzeIMfxhhy2BQiYAbSsQ-A3s

Low Level Design:
https://docs.google.com/document/d/1JIJEH3vPydKhve3Jf1kFQJqXouOoUZARGSo3mAL5Klk

Frontend Design:
https://docs.google.com/document/d/17j-aY47cF7_nJClIue6899WyDTA5rCpJ63IcZVyBg2s

# Upcoming Features

- Display status of request inside color badge
- When Approver level is assigned to another user, the active requests and active approvals will be updated with new assignee
- When new Approver level is added, the active requests will be updated and active approvals will be added with new assignee
- Buttons to update payment status from INITIATED to PENDING to COMPLETED / REJECTED
- Notifications list can be shown to user and they will get redirect link for them
- Roles and Permissions assigned to each user for which page they can access
- Add Filtering and Search logic to Data table
- Admin can be able to do CRUD operations on users
- Users can opt how they want to receive notifications and Notifications can be sent to user to all those ways (Email, SMS)
- A scheduler can find out if any approval is pending and configured days left for deadline, then send automated notification to the assigned user
- User will be able to send manual notification to any other user related to any request assigned to them
- While raising request if no approver level is assigned, then directly send request for payment
- Option to mark any Notification as unseen
- Option to mark all notifications as seen
- Every company can customize and add their own template for Project and Request
- Every company can have it's own Email and SMS template
- Home screen should display all the analysis (Like how many requests raised per project, amounts etc.) in the form of graph
- Integration with payment gateway so that users can do payment with single click
- Payment can be assigned to any user
  - Project should contain a column PaymentAsignee
  - When Payment record is created, it should be assigned automatically to Project's PaymentAsignee
  - When PaymentAsignee is updated, it should be updated in all the active Payments

# References

- [Angular DataTables:](http://l-lin.github.io/angular-datatables/#/welcome)
- [MDBootstrap:](https://mdbootstrap.com/docs/standard/getting-started/installation)
- [Send sms using aws simple notification service (SNS) and Spring boot](https://www.rajith.me/2020/03/send-sms-using-aws-simple-notification.html)
- [How to refresh a component from another in angular](https://stackoverflow.com/questions/63888794/how-to-refresh-a-component-from-another-in-angular#:~:text=To%20refresh%2C%20or%20better%20to,from%20APIs%20for%20CRUD%20operations.)
- [Markdown Basic Syntax](https://www.markdownguide.org/basic-syntax/)
