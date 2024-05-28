String sql = "call order_pkg_create_order(?, ?, ?)";
drop sequence orders_seq;
create sequence orders_seq
    start with 1
    increment by 1
    nocycle
    cache 20;
CREATE TABLE Orders (
                        order_id INTEGER,
                        order_date timestamp,
                        order_amount INTEGER,
                        book_id INTEGER,
                        user_id INTEGER
);


create or replace package order_pkg as
    procedure create_order(
        p_book_id books.book_id%type,
        p_user_id users.user_id%type,
        p_book_price books.book_price%type
    );
    procedure find_order_count(
        p_book_id books.book_id%type,
        p_user_id users.user_id%type,
        p_order out sys_refcursor
    );
end order_pkg;
/

create or replace package body order_pkg as
    procedure create_order(
        p_book_id books.book_id%type,
        p_user_id users.user_id%type,
        p_book_price books.book_price%type
    ) as
    begin
        insert into orders (order_id, order_date, order_amount, book_id, user_id)
        values (orders_seq.nextval, sysdate, p_book_price, p_book_id, p_user_id);
    end create_order;

    procedure find_order_count(
        p_book_id books.book_id%type,
        p_user_id users.user_id%type,
        p_order out sys_refcursor
    ) as
    begin
        open p_order for
            select count(*) as order_count
            from orders
            where book_id = p_book_id and user_id = p_user_id;
    end find_order_count;
end order_pkg;
/