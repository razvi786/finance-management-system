/*MySQL Scripts*/

USE fms;

create table user(
user_id integer NOT NULL AUTO_INCREMENT,
role_id integer,
name varchar(255),
email varchar(255) UNIQUE,
password varchar(255),
phone varchar(50) UNIQUE,
created_datetime timestampz NOT NULL,
updated_datetime timestampz,
concurrency_version integer NOT NULL,
CONSTRAINT pk_user PRIMARY KEY (user_id),
CONSTRAINT fk_01_user_role FOREIGN KEY (role_id) references role(role_id));

create table project(
project_id integer NOT NULL AUTO_INCREMENT,
user_id integer,
approver_level_id integer,
name varchar(255),
description varchar(255),
budget integer,
created_datetime timestampz NOT NULL,
updated_datetime timestampz,
concurrency_version integer NOT NULL,
CONSTRAINT pk_project PRIMARY KEY (project_id),
CONSTRAINT fk_01_project_user FOREIGN KEY (user_id) references user(user_id),
CONSTRAINT fk_02_project_approver_level FOREIGN KEY (approver_level_id) references approver_level(approver_level_id));

create table vendor(
vendor_id integer NOT NULL AUTO_INCREMENT,
account_holder_name varchar(255),
account_number varchar(50),
ifsc_code varchar(255),
bank_name varchar(255),
branch varchar(255),
upi_id varchar(255),
created_datetime timestampz NOT NULL,
updated_datetime timestampz,
concurrency_version integer NOT NULL,
CONSTRAINT pk_vendor PRIMARY KEY (vendor_id));

create table approver_level(
approver_level_id integer NOT NULL AUTO_INCREMENT,
project_id integer,
approver_id integer,
level integer DEFAULT '1',
created_datetime timestampz NOT NULL,
updated_datetime timestampz,
concurrency_version integer NOT NULL,
CONSTRAINT pk_approver_level PRIMARY KEY (approver_level_id),
CONSTRAINT fk_01_approver_level_project FOREIGN KEY (project_id) references project(project_id),
CONSTRAINT fk_02_approver_level_user FOREIGN KEY (approver_id) references user(user_id));
