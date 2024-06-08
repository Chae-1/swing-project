create sequence book_discussions_seq
    start with 1
    increment by 1
    nocycle
    cache 20;

create or replace type book_disc_obj as object
(
    book_id integer,
    user_id integer,
    disc_title varchar2(100),
    disc_content clob
);
/


create or replace package book_disc_pkg as
    procedure add_book_disc(
        p_book_disc in book_disc_obj
    );
    procedure find_all(
        p_book_id books.book_id%type,
        p_book_disc out sys_refcursor
    );

end book_disc_pkg;
/


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
        p_book_disc out sys_refcursor
    ) as
    begin
        open p_book_disc for
            select bd.*, u.user_name
            from bookdiscussions bd
                     join users u on u.user_id = bd.user_id
            where book_id = p_book_id;
    end find_all;
end book_disc_pkg;
/

create or replace type disc_com_reg as object
(
    user_id integer,
    discussion_id integer,
    reg_date timestamp,
    com_content clob
)
/

create or replace package disc_com_pkg as
    procedure register_disc_comment(
        p_disc_com_form in disc_com_reg
    );
    procedure find_disc_comment(
        p_disc_id in DiscussionComments.dis_comment_id%type,
        p_comment out sys_refcursor
    );
    procedure find_comment_by_id(
        p_disc_comment_id in DiscussionComments.dis_comment_id%type,
        p_comment out sys_refcursor
    );
    procedure remove_by_id(
        p_disc_commentId DiscussionComments.dis_comment_id%type
    );
end disc_com_pkg;
/

create or replace package body disc_com_pkg as
    procedure register_disc_comment(
        p_disc_com_form in disc_com_reg
    ) as
    begin
        insert into DiscussionComments
            values (discussion_comments_seq.nextval, p_disc_com_form.reg_date,
                    p_disc_com_form.com_content, p_disc_com_form.discussion_id, p_disc_com_form.user_id, null);
    end register_disc_comment;

    procedure find_disc_comment(
        p_disc_id in DiscussionComments.dis_comment_id%type,
        p_comment out sys_refcursor
    ) as
    begin
        open p_comment for
            select dc.*, u.user_name
            from DiscussionComments dc
            join users u on dc.user_id = u.user_id
            where dc.discussion_id = p_disc_id;
    end find_disc_comment;

    procedure find_comment_by_id(
        p_disc_comment_id in DiscussionComments.dis_comment_id%type,
        p_comment out sys_refcursor
    ) as
    begin
        open p_comment for
            select dc.*, u.user_name
            from DiscussionComments dc
                     join users u on u.user_id = dc.user_id
            where dc.dis_comment_id = p_disc_comment_id;
    end find_comment_by_id;

    procedure remove_by_id(
        p_disc_commentId in DiscussionComments.dis_comment_id%type
    ) as
    begin
        delete DiscussionComments
        where dis_comment_id = p_disc_commentId;
    end remove_by_id;
end disc_com_pkg;
/