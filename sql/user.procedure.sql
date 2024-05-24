drop table users;
create table users (
                       user_id int,
                       user_name VARCHAR2(30),
                       user_password VARCHAR2(30),
                       user_log_id VARCHAR2(30),
                       user_registration_date timestamp,
                       user_image VARCHAR2(30)
);

create unique index idx_users on users(user_id);
alter table users add constraint users_pk primary key(user_id);
alter table users add constraint user_name_nn check(user_name is not null);
alter table users add constraint user_password_nn check(user_password is not null);

drop sequence users_seq;
create sequence users_seq
    start with 1
    increment by 1
    nocycle
  cache 20;

create or replace type user_form as object (
    user_name VARCHAR2(30),
    user_password VARCHAR2(30),
    user_log_id VARCHAR2(30),
    user_registration_date timestamp,
    user_image VARCHAR2(30)
);

create or replace package user_pkg as
    procedure add_user(p_user_form in user_form);
    procedure find_user_by_logid(
        p_user_log_id in users.user_log_id%type,
        p_user_password in users.user_password%type,
        p_user out SYS_REFCURSOR
    );
    --procedure find_all_user();
    procedure delete_user_by_id(p_user_id in users.user_id%type);
    -- user 변경 및 선호 카테고리를 변경할 수 있도록 설정
end user_pkg;

create or replace package body user_pkg as
procedure add_user(p_user_form in user_form) as
begin
insert into users(user_id, user_name, user_password,
                  user_log_id, user_registration_date, user_image)
values (users_seq.nextval, p_user_form.user_name,
        p_user_form.user_password, p_user_form.user_log_id,
        p_user_form.user_registration_date, p_user_form.user_image);
end add_user;


    procedure find_user_by_logid(
        p_user_log_id in users.user_log_id%type,
        p_user_password in users.user_password%type,
        p_user out SYS_REFCURSOR
    ) as
begin
open p_user for
select u.*
from users u
where user_id = p_user_log_id and user_password =  p_user_password;
end find_user_by_logid;

    procedure delete_user_by_id(p_user_id in users.user_id%type) as
begin
delete from users
where user_id = p_user_id;
end delete_user_by_id;
end user_pkg;
/
