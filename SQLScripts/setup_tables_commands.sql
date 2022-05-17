/*MySQL Insert Scripts*/

USE fms;

INSERT INTO fms.permission (`permission_id`, `name`) VALUES
(1, 'Create'),
(2, 'Update'),
(3, 'Delete'),
(4, 'View');

INSERT INTO fms.role (`role_id`, `name`) VALUES
(5, 'Employee'),
(6, 'Admin');

INSERT INTO fms.role_permission (`role_id`, `permission_id`) VALUES
(5, 4),
(5, 1),
(6, 1),
(6, 2),
(6, 3),
(6, 4);

INSERT INTO fms.user (`user_id`, `role_id`, `name`, `email`, `password`, `phone`) VALUES
(13, 6, 'Ehsan', 'ehsan@gmail.com', '12345', '1234567890'),
(14, 5, 'Vaishnavi', 'vaishnavi@gmail.com', '54321', '1234567980');

INSERT INTO fms.project (`project_id`, `user_id`, `name`, `description`, `budget`) VALUES
(15, 13, 'HackFSE', 'Programming for associates', 100000);

INSERT INTO fms.approver_level (`approver_level_id`, `project_id`, `approver_id`, `level`) VALUES
(16, 15, 14, 0),
(17, 15, 13, 1);