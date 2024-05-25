drop table comments;
CREATE TABLE comments (
                          comment_id INTEGER,
                          comment_content CLOB,
                          comment_rating INTEGER,
                          comment_reg_date timestamp,
                          comment_is_purchased VARCHAR2(20),
                          book_id INTEGER,
                          user_id INTEGER
);

CREATE TABLE Books (
                       book_id INTEGER,
                       book_title VARCHAR2(50),
                       book_author VARCHAR2(30),
                       book_publication_date DATE,
                       book_sales_point INTEGER,
                       book_summary CLOB,
                       book_description CLOB,
                       book_price INTEGER,
                       book_rating number(2, 1),
                       book_publisher VARCHAR2(50)
);

create table users (
                       user_id int,
                       user_name VARCHAR2(30),
                       user_password VARCHAR2(30),
                       user_log_id VARCHAR2(30),
                       user_registration_date timestamp,
                       user_image VARCHAR2(30)
);

drop sequence comments_seq;

create sequence comments_seq
    start with 1
    increment by 1
    nocycle
    cache 20;

create unique index idx_comments on comments(comment_id);
alter table comments add constraint comments_pk primary key (comment_id);
alter table comments add constraint comments_book_fk foreign key (book_id) references books (book_id) on delete cascade;
alter table comments add constraint comments_users_fk foreign key (user_id) references users (user_id) on delete cascade;
alter table comments add constraint comments_book_id_nn check(book_id is not null);
alter table comments add constraint comments_user_id_nn check(user_id is not null);


create or replace type comment_reg as object
(
    user_id int,
    book_id INTEGER,
    comment_rating INTEGER,
    comment_reg_date timestamp,
    comment_content CLOB,
    comment_is_purchased VARCHAR2(20)
);

create or replace package comment_pkg is
    procedure register_comment(
        p_comment in comment_reg
    );
    procedure find_book_comments(
        p_book_id in books.book_id%type,
        p_comment out sys_refcursor
    );
end comment_pkg;
/

create or replace package body comment_pkg is
    procedure register_comment(
        p_comment in comment_reg
    ) as
    begin
        insert into comments
        values (comments_seq.nextval, p_comment.comment_content, p_comment.comment_rating, p_comment.comment_reg_date,
                p_comment.comment_is_purchased, p_comment.book_id, p_comment.user_id);
    end register_comment;
    procedure find_book_comments(
        p_book_id in books.book_id%type,
        p_comment out sys_refcursor
    ) as
    begin
        open p_comment for
            select c.comment_id, u.user_name, c.comment_rating, c.comment_reg_date, c.comment_content
            from comments c
                     join users u on c.user_id = u.user_id
            where c.book_id = p_book_id;
    end find_book_comments;
end comment_pkg;
/
