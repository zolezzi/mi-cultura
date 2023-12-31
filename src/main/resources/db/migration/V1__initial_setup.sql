drop table if exists account_review_place;
drop table if exists account_interest_place;
drop table if exists account_review_event;
drop table if exists account_interest_event;
drop table if exists user;
drop table if exists review;
drop table if exists account;

create table account(id bigint primary key AUTO_INCREMENT, account_role varchar(255) not null, dni varchar(255) not null, firstname varchar(255) not null, lastname varchar(255) not null, phone_number varchar(255) not null, address varchar(255) not null);
create table user(id bigint primary key AUTO_INCREMENT, email varchar(255) not null, password varchar(255) not null, account_id bigint, constraint fk_user_account FOREIGN KEY (account_id) REFERENCES account(id));

INSERT INTO account(account_role, dni, firstname, lastname, phone_number, address) VALUES ('ADMIN', '36001205', 'ADMIN', 'MICULTURA', '1123546798', 'Av. Alvear 1690');
INSERT INTO user (email, password, account_id) VALUES ('adminmicultura@gmail.com', 'SG9sYVBsYW5ldGExMSE=', 1);