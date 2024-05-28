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


alter table books add book_image_url varchar2(255);

-- 구조체 타입 정의
CREATE OR REPLACE TYPE ORDER_FORM AS OBJECT (
    order_id         NUMBER,
    book_id          number,
    book_title       VARCHAR2(255),
    purchased_date   TIMESTAMP,
    price            NUMBER,
    book_image_url   VARCHAR2(255)
);
/

-- 테이블 타입 정의
CREATE OR REPLACE TYPE ORDER_TABLE AS TABLE OF ORDER_FORM;
/
drop type order_table;

-- 패키지 정의
CREATE OR REPLACE PACKAGE order_pkg AS
    PROCEDURE create_order(
        p_book_id books.book_id%type,
        p_user_id users.user_id%type,
        p_book_price books.book_price%type
    );
<<<<<<< HEAD

    PROCEDURE find_order_count(
           p_book_id books.book_id%type,
           p_user_id users.user_id%type,
           p_order OUT SYS_REFCURSOR
    );

    PROCEDURE find_orders_by_user_id(
        p_user_id users.user_id%type,
        p_orders OUT ORDER_TABLE
    );
END order_pkg;
/

-- 패키지 바디 정의
CREATE OR REPLACE PACKAGE BODY order_pkg AS
    PROCEDURE create_order(
        p_book_id books.book_id%type,
        p_user_id users.user_id%type,
        p_book_price books.book_price%type
    ) AS
    BEGIN
        INSERT INTO orders (order_id, order_date, order_amount, book_id, user_id)
        VALUES (orders_seq.nextval, SYSDATE, p_book_price, p_book_id, p_user_id);
    END create_order;

<<<<<<< HEAD
    PROCEDURE find_order_count(
               p_book_id books.book_id%type,
               p_user_id users.user_id%type,
               p_order OUT SYS_REFCURSOR
    ) AS
    BEGIN
        OPEN p_order FOR
        SELECT COUNT(*) AS order_count
        FROM orders
        WHERE book_id = p_book_id AND user_id = p_user_id;
    END find_order_count;

    PROCEDURE find_orders_by_user_id(
        p_user_id users.user_id%type,
        p_orders OUT ORDER_TABLE
    ) AS
    BEGIN
        SELECT ORDER_FORM(
            o.order_id,
            b.book_id,
            b.book_title,
            o.order_date,
            o.order_amount,
            b.book_image_url
        )
        BULK COLLECT INTO p_orders
        FROM orders o
        JOIN books b ON o.book_id = b.book_id
        WHERE o.user_id = p_user_id
        ORDER BY o.order_date DESC;
    END find_orders_by_user_id;

end order_pkg;
/