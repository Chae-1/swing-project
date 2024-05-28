-- public record BookDiscussionRegisterForm(Long bookId,
--                                          Long userId,
--                                          String title,
--                                          String content) {
-- }

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

create or replace type book_disc_obj as object
(
    book_id integer,
    user_id integer,
    disc_title varchar2(30),
    disc_content clob
);

create or replace package book_disc_pkg as
    procedure add_book_disc(
        p_book_disc in book_disc_obj
    );
    procedure find_all(
        p_book_id books.book_id%type,
        p_book_disc sys_refcursor
    );

end book_disc_pkg;

create or replace package body book_disc_pkg as
    procedure add_book_disc(
        p_book_disc in book_disc_obj
    )as
    begin
        insert into BookDiscussions(discussion_id,
                                    discussion_date,
                                    discussion_content,
                                    discussion_title,
                                    book_id,
                                    user_id)
        values (book_discussions_seq.nextval, sysdate, p_book_disc.disc_content, p_book_disc.disc_title,
                p_book_disc.book_id, p_book_disc.user_id);
    end add_book_disc;

    procedure find_all(
        p_book_id books.book_id%type,
        p_book_disc sys_refcursor
    ) as
    begin
        open p_book_disc for
            select *
            from bookdiscussion
            where book_id = p_book_id;
    end find_all;

end book_disc_pkg;