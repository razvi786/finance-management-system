/*MySQL Scripts*/

USE fms;

SET SQL_MODE='ALLOW_INVALID_DATES';

CREATE TABLE IF NOT EXISTS fms.permission(
permission_id integer NOT NULL AUTO_INCREMENT,
name varchar(100) NOT NULL,
created_datetime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
updated_datetime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
concurrency_version integer NOT NULL,
CONSTRAINT pk_permission PRIMARY KEY (permission_id));

CREATE TABLE IF NOT EXISTS fms.role(
role_id integer NOT NULL AUTO_INCREMENT,
name varchar(100) NOT NULL,
created_datetime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
updated_datetime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
concurrency_version integer NOT NULL,
CONSTRAINT pk_role PRIMARY KEY (role_id));

CREATE TABLE IF NOT EXISTS fms.role_permission(
role_permission_id integer NOT NULL AUTO_INCREMENT,
role_id integer NOT NULL,
permission_id integer NOT NULL,
created_datetime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
updated_datetime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
concurrency_version integer NOT NULL,
CONSTRAINT pk_role_permission PRIMARY KEY (role_permission_id),
CONSTRAINT fk_01_role_permission FOREIGN KEY (role_id) references role(role_id),
CONSTRAINT fk_02_role_permission FOREIGN KEY (permission_id) references permission(permission_id));

CREATE TABLE IF NOT EXISTS fms.user(
user_id integer NOT NULL AUTO_INCREMENT,
role_id integer NOT NULL,
name varchar(255) NOT NULL,
email varchar(255) NOT NULL UNIQUE,
password varchar(255) NOT NULL,
phone varchar(50) NOT NULL UNIQUE,
created_datetime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
updated_datetime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
concurrency_version integer NOT NULL,
CONSTRAINT pk_user PRIMARY KEY (user_id),
CONSTRAINT fk_01_user_role FOREIGN KEY (role_id) references role(role_id));

CREATE TABLE IF NOT EXISTS fms.project(
project_id integer NOT NULL AUTO_INCREMENT,
user_id integer NOT NULL,
name varchar(255) NOT NULL,
description varchar(255),
budget integer,
created_datetime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
updated_datetime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
concurrency_version integer NOT NULL,
CONSTRAINT pk_project PRIMARY KEY (project_id),
CONSTRAINT fk_01_project_user FOREIGN KEY (user_id) references user(user_id));

CREATE TABLE IF NOT EXISTS fms.vendor(
vendor_id integer NOT NULL AUTO_INCREMENT,
account_holder_name varchar(255) NOT NULL,
account_number varchar(50) NOT NULL,
ifsc_code varchar(255) NOT NULL,
bank_name varchar(255) NOT NULL,
branch varchar(255),
upi_id varchar(255),
created_datetime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
updated_datetime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
concurrency_version integer NOT NULL,
CONSTRAINT pk_vendor PRIMARY KEY (vendor_id));

CREATE TABLE IF NOT EXISTS fms.approver_level(
approver_level_id integer NOT NULL AUTO_INCREMENT,
project_id integer NOT NULL,
approver_id integer NOT NULL,
level integer NOT NULL DEFAULT '1',
created_datetime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
updated_datetime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
concurrency_version integer NOT NULL,
CONSTRAINT pk_approver_level PRIMARY KEY (approver_level_id),
CONSTRAINT fk_01_approver_level_project FOREIGN KEY (project_id) references project(project_id),
CONSTRAINT fk_02_approver_level_user FOREIGN KEY (approver_id) references user(user_id));

CREATE TABLE IF NOT EXISTS fms.notification(
notification_id integer NOT NULL AUTO_INCREMENT,
notification_type ENUM('AUTOMATED', 'MANUAL'),
message varchar(255),
triggered_by integer NOT NULL,
triggered_to integer NOT NULL,
seen_datetime timestamp,
sms_sent_datetime timestamp,
email_sent_datetime timestamp,
created_datetime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
updated_datetime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
concurrency_version integer NOT NULL,
CONSTRAINT pk_notification PRIMARY KEY (notification_id),
CONSTRAINT fk_01_user_notification FOREIGN KEY (triggered_by) references user(user_id),
CONSTRAINT fk_02_user_notification FOREIGN KEY (triggered_to) references user(user_id));
