# finance-management-system

Database Design:
https://docs.google.com/document/d/14e5gAoa-3U7Aa53yY3iOqQ9UIkpsmGsKKylFti7gUKw

# Upcoming Features

- Display status of request inside color badge
- When Approver level is assigned to another user, the active requests and active approvals will be updated with new assignee
- When new Approver level is added, the active requests will be updated and active approvals will be added with new assignee
- Buttons to update payment status from INITIATED to PENDING to COMPLETED / REJECTED
- Notifications list can be shown to user and they will get redirect link for them
- Roles and Permissions assigned to each user for which page they can access
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
