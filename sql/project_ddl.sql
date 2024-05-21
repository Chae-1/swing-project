create table users (
                       user_id int,
                       user_name VARCHAR(30),
                       user_password VARCHAR(30) NOT NULL,
                       user_log_id VARCHAR(30),
                       user_registration_date timestamp,
                       user_image VARCHAR(30)
);

create unique index idx_user_id on users(user_id);
alter table users add constraint user_pk primary key(user_id);
alter table users add constraint user_name_nn check(user_name is not null);
alter table users add constraint user_password_nn check(user_password is not null);

create sequence users_seq
    start with 1
    increment by 1
    nocycle
  cache 20;


CREATE TABLE Category (
                          category_id INTEGER PRIMARY KEY,
                          category_name VARCHAR(50) NOT NULL,
                          prior_category_id INTEGER,
                          FOREIGN KEY (prior_category_id) REFERENCES Category(category_id)
);

create unique index idx_category_id on Category(category_id);
alter table category add constraint category_pk primary key(category_id);
alter table category add constraint category_name_nn check(category_name is not null);
alter table category add constraint prior_category_fk foreign key (prior_category_id) references category(category_id);

create sequence category_seq
    start with 1
    increment by 1
    nocycle
cache 20;

CREATE TABLE Book (
                      book_id INTEGER,
                      book_title VARCHAR(50),
                      book_author VARCHAR(30),
                      book_publication_date DATE,
                      book_sales_point INTEGER,
                      book_summary CLOB,
                      book_description CLOB,
                      book_price INTEGER,
                      book_rating number(2, 1),
                      book_publisher VARCHAR(50)
);

create unique index idx_book_id on book(book_id);
alter table book add constraint book_pk primary key(book_id);
alter table book add constraint book_title_nn check( book_title is not null);
alter table book add constraint book_author_nn check( book_author is not null);

create sequence book_seq
    start with 1
    increment by 1
    nocycle
cache 20;

select * FROM BOOK_SEQUENCES;

CREATE TABLE BookCategory (
                              category_id INTEGER,
                              book_id INTEGER
);

select *
from user_constraints
where table_name = 'BOOKCATEGORY';

create unique index idx_book_category on bookcategory(book_id, category_id);
alter table bookcategory add constraint bookcategory_pk primary key(book_id, category_id);
alter table bookcategory add constraint bookcategory_category_fk foreign key (category_id) references category(category_id);
alter table bookcategory add constraint bookcategory_book_fk foreign key (book_id) references book(book_id);


CREATE TABLE Order (
                       order_id INTEGER,
                       order_date timestamp,
                       order_amount INTEGER,
                       book_id INTEGER,
                       user_id INTEGER,
);

create unique index idx_order on Order(order_id);
alter table order add constraint order_pk primary key(order_id);
alter table order add constraint order_book_fk foreign key(book_id) references book (book_id);
alter table order add constraint order_users_fk foreign key(users_id) references users (users_id);


CREATE TABLE Comment (
                         comment_id INTEGER PRIMARY KEY,
                         comment_content CLOB,
                         comment_rating INTEGER,
                         comment_field DATE,
                         comment_is_purchased VARCHAR(20),
                         book_id INTEGER,
                         user_id INTEGER,
                         FOREIGN KEY (book_id) REFERENCES Book(book_id),
                         FOREIGN KEY (user_id) REFERENCES User(user_id)
);


CREATE TABLE BookDiscussion (
                                discussion_id INTEGER PRIMARY KEY,
                                discussion_date DATETIME,
                                discussion_content CLOB,
                                discussion_title VARCHAR(30),
                                book_id INTEGER,
                                user_id INTEGER,
                                is_purchased INTEGER,
                                FOREIGN KEY (book_id) REFERENCES Book(book_id),
                                FOREIGN KEY (user_id) REFERENCES User(user_id)
);

CREATE TABLE DiscussionComment (
                                   dis_comment_id INTEGER PRIMARY KEY,
                                   dis_comment_registration_date DATETIME,
                                   dis_comment_content VARCHAR,
                                   discussion_id INTEGER,
                                   user_id INTEGER,
                                   dis_comment_is_purchased VARCHAR(20),
                                   FOREIGN KEY (discussion_id) REFERENCES BookDiscussion(discussion_id),
                                   FOREIGN KEY (user_id) REFERENCES User(user_id)
);

CREATE TABLE PreferCategory (
                                category_id INTEGER,
                                user_id INTEGER,
                                PRIMARY KEY (category_id, user_id),
                                FOREIGN KEY (category_id) REFERENCES Category(category_id),
                                FOREIGN KEY (user_id) REFERENCES User(user_id)
);