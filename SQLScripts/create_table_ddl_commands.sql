create type notification_type as ENUM 
('AUTOMATED', 'MANUAL');

create table notification(
notification_id integer NOT NULL,
notification_type notification_type enum NOT NULL,
message varchar(350),
triggered_by integer NOT NULL,
triggered_to integer NOT NULL,
seen_datetime timestampz NOT NULL,
email_sent_datetime timestampz NOT NULL,
sms_sent_datetime timestampz NOT NULL,
created_datetime timestampz NOT NULL,
updated_datetime timestampz NOT NULL,
concurrency_version integer NOT NULL,
CONSTRAINT pk_notification PRIMARY KEY (notification_id),
CONSTRAINT fk_01_user_notification FOREIGN KEY (triggered_by) references user(user_id),
CONSTRAINT fk_02_user_notification FOREIGN KEY (triggered_to) references user(user_id));

create table permission(
permission_id integer NOT NULL,
name varchar(100) NOT NULL,
created_datetime timestampz NOT NULL,
updated_datetime timestampz NOT NULL,
concurrency_version integer NOT NULL,
CONSTRAINT pk_permission PRIMARY KEY (permission_id)};

create table role(
role_id integer NOT NULL,
user_id integer NOT NULL,
permission_id integer NOT NULL,
name varchar(100) NOT NULL,
created_datetime timestampz NOT NULL,
updated_datetime timestampz NOT NULL,
concurrency_version integer NOT NULL,
CONSTRAINT pk_role PRIMARY KEY (role_id),
CONSTRAINT fk_01_user_role FOREIGN KEY (user_id) references user(user_id),
CONSTRAINT fk_02_user_role FOREIGN KEY (permission_id) references permission(permission_id));

create table role_permission(
role_permission_id integer NOT NULL,
role_id integer NOT NULL,
permission_id integer NOT NULL,
created_datetime timestampz NOT NULL,
updated_datetime timestampz NOT NULL,
concurrency_version integer NOT NULL,
CONSTRAINT pk_role_permission PRIMARY KEY (role_permission_id),
CONSTRAINT fk_01_role_permission FOREIGN KEY (role_id) references role(role_id),
CONSTRAINT fk_02_role_permission FOREIGN KEY (permission_id) references permission(permission_id));


/*MongDB Scripts*/
use mongoDB
db.createCollection("approver")

db.mongoDB.insert({
})