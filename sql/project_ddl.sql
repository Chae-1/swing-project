-- ddl
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

drop table categories;
CREATE TABLE Categories (
                            category_id INTEGER,
                            category_name VARCHAR2(50),
                            prior_category_id INTEGER
);
drop sequence categories_seq;
create sequence categories_seq
    start with 1
    increment by 1
    nocycle
cache 20;

create unique index idx_categories on Categories(category_id);
alter table categories add constraint category_pk primary key(category_id);
alter table categories add constraint category_name_nn check(category_name is not null);
alter table categories add constraint prior_category_fk foreign key (prior_category_id) references categories(category_id) on delete cascade;


drop table books;
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
                       book_publisher VARCHAR2(50),
                       book_image_url varchar2(3000)
);

create unique index idx_books on books(book_id);
alter table books add constraint books_pk primary key(book_id);
alter table books add constraint books_title_nn check(book_title is not null);
alter table books add constraint books_author_nn check(book_author is not null);

drop sequence books_seq;
create sequence books_seq
    start with 1
    increment by 1
    nocycle
cache 20;

drop table BookCategories;
CREATE TABLE BookCategories (
                                book_category_id integer,
                                category_id INTEGER,
                                book_id INTEGER
);

create unique index idx_book_categories on bookcategories(book_id, category_id);
alter table bookcategories add constraint bookcategories_pk primary key(book_id, category_id);
create sequence book_categories_seq
    start with 1
    increment by 1
    nocycle
    cache 20;

drop sequence BookCategories_seq;
create sequence BookCategories_seq
    start with 1
    increment by 1
    nocycle
    cache 20;


create unique index idx_book_categories on bookcategories(book_category_id);
alter table bookcategories add constraint bookcategories_pk primary key(book_category_id);
alter table bookcategories add constraint bookcategories_category_fk foreign key (category_id) references categories(category_id) on delete cascade;
alter table bookcategories add constraint bookcategories_book_fk foreign key (book_id) references books(book_id) on delete cascade;


drop table orders;
CREATE TABLE Orders (
                        order_id INTEGER,
                        order_date timestamp,
                        order_amount INTEGER,
                        book_id INTEGER,
                        user_id INTEGER
);

drop sequence orders_seq;
create sequence orders_seq
    start with 1
    increment by 1
    nocycle
    cache 20;

create unique index idx_orders on orders(order_id);
alter table orders add constraint order_pk primary key(order_id);
alter table orders add constraint order_book_fk foreign key(book_id) references books (book_id) on delete cascade;
alter table orders add constraint order_users_fk foreign key(user_id) references users (user_id) on delete cascade;
alter table orders add constraint order_book_id_nn check(book_id is not null);
alter table orders add constraint order_user_id_nn check(user_id is not null);

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


drop table BookDiscussions;
CREATE TABLE BookDiscussions (
                                 discussion_id INTEGER,
                                 discussion_date timestamp,
                                 discussion_content CLOB,
                                 discussion_title VARCHAR2(30),
                                 book_id INTEGER,
                                 user_id INTEGER
);

create sequence book_discussions_seq
    start with 1
    increment by 1
    nocycle
cache 20;

create unique index idx_book_discussions on BookDiscussions(discussion_id);
alter table bookdiscussions add constraint book_discussion_pk primary key (discussion_id);
alter table bookdiscussions add constraint book_discussion_book_fk foreign key (book_id) references books (book_id) on delete cascade;
alter table bookdiscussions add constraint book_discussion_users_fk foreign key (user_id) references users (user_id) on delete cascade;
alter table bookdiscussions add constraint book_discussion_book_id_nn check(book_id is not null);
alter table bookdiscussions add constraint book_discussion_user_id_nn check(user_id is not null);

drop table DiscussionComments;
CREATE TABLE DiscussionComments (
                                    dis_comment_id INTEGER,
                                    dis_comment_registration_date timestamp,
                                    dis_comment_content clob,
                                    discussion_id INTEGER,
                                    user_id INTEGER
);

create sequence discussion_comments_seq
    start with 1
    increment by 1
    nocycle
cache 20;

create unique index idx_discussion_comments on Discussioncomments(dis_comment_id);
alter table Discussioncomments add constraint discussion_comments_pk primary key (dis_comment_id);
alter table Discussioncomments add constraint discussion_comments_dis_fk foreign key (discussion_id) references bookdiscussions(discussion_id) on delete cascade;
alter table Discussioncomments add constraint discussion_comments_users_fk foreign key (user_id) references users (user_id) on delete cascade;
alter table Discussioncomments add constraint discussion_comments_discussion_id_nn check(discussion_id is not null);
alter table Discussioncomments add constraint discussion_comments_user_id_nn check(user_id is not null);


drop table PreferCategories;
CREATE TABLE PreferCategories (
                                  category_id INTEGER,
                                  user_id INTEGER
);

create unique index idx_prefer_categories on PreferCategories(user_id, category_id);
alter table PreferCategories add constraint prefer_categories_pk primary key(user_id, category_id);
alter table PreferCategories add constraint prefer_categories_users_fk foreign key (user_id) references users(user_id) on delete cascade;
alter table PreferCategories add constraint prefer_categories_category_fk foreign key (category_id) references categories(category_id) on delete cascade;


-- trigger
create or replace trigger trg_after_insert_orders
after insert on orders
for each row
begin
    update books
    set book_sales_point = book_sales_point + 1
    where book_id = :new.book_id;
end;
/
