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
    procedure find_comment_by_id(
        p_comment_id in comments.comment_id%type,
        p_user_id in comments.user_id%type,
        p_comment out sys_refcursor
    );
    procedure remove_comment(
        p_comment_id in comments.comment_id%type
    );

    procedure find_user_comment_on_book(
        p_user_id in users.user_id%type,
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

        update books
        set book_rating = (
            select nvl(avg(comment_rating), 0)
            from comments
            where book_id = p_comment.book_id and comment_is_purchased = 'Y'
        )
        where book_id = p_comment.book_id;
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
    procedure find_comment_by_id(
        p_comment_id in comments.comment_id%type,
        p_user_id in comments.user_id%type,
        p_comment out sys_refcursor
    ) as
    begin
        open p_comment for
            select *
            from comments
            where comment_id = p_comment_id and user_id = p_user_id;
    end find_comment_by_id;
    procedure remove_comment(
        p_comment_id in comments.comment_id%type
    ) as
        p_book_id books.book_id%type;
    begin

        select book_id into p_book_id
        from comments
        where comment_id = p_comment_id;

        delete comments
        where comment_id = p_comment_id;

        update books
        set book_rating = (
            select nvl(avg(nvl(comment_rating, 0)), 0)
            from comments
            where book_id = p_book_id and comment_is_purchased = 'Y'
        )
        where book_id = p_book_id;
    end remove_comment;
    procedure find_user_comment_on_book(
        p_user_id in users.user_id%type,
        p_book_id in books.book_id%type,
        p_comment out sys_refcursor
    ) as
    begin
        open p_comment for
            select *
            from comments
            where user_id = p_user_id and book_id = p_book_id;
    end find_user_comment_on_book;

end comment_pkg;
/
