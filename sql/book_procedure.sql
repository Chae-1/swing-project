-- 프로시저의 in, out 모드 -
-- 파라미터는 호출 시 값이 전달되는 읽기 전용 인자다. -> final
-- in 모드는 사용은 가능하지만, 수정할 수는 없는 프로시저의 인자다.
-- out 모드는 프로시저나 함수가 값을 반환하기 위해 사용하는 인자이다.
-- 프로시저나 함수가 작업 결과를 호출자에게 반환할 때 사용한다.
-- ex ) 프로시저 내부에서 전달된 in 인자는 내부에서 사용되기만 한다.
-- -- out 인자는 호출 결과로 리턴받을 수 있다.
CREATE TABLE Books (
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

create unique index idx_books on books(book_id);
alter table books add constraint books_pk primary key(book_id);
alter table books add constraint books_title_nn check(book_title is not null);
alter table books add constraint books_author_nn check(book_author is not null);

CREATE OR REPLACE TYPE book_info_rec AS OBJECT (
    book_title VARCHAR2(50),
    book_author VARCHAR2(30),
    book_publication_date DATE,
    book_sales_point INTEGER,
    book_summary CLOB,
    book_description CLOB,
    book_price INTEGER,
    book_rating NUMBER(2, 1),
    book_publisher VARCHAR2(50)
);

CREATE OR REPLACE TYPE book_rec as OBJECT (
        book_id INTEGER,
        book_info book_info_rec
);


create or replace package book_pkg as

    procedure add_book(
        book_info in book_info_rec
    );

    procedure find_book_by_title(
        p_book_title in books.book_title%type,
        p_book OUT SYS_REFCURSOR
    );

    procedure delete_book(
       p_book_id in books.book_id%type
    );

    procedure update_book(
       p_book_id in books.book_id%type,
       book_info in book_info_rec
    );

    procedure find_book_by_id(
        p_book_id in books.book_id%type,
        p_books OUT SYS_REFCURSOR
    );

    procedure find_all_book(
        p_books OUT SYS_REFCURSOR
    );
end book_pkg;
/

create or replace package body book_pkg as
procedure add_book(book_info in book_info_rec) as
begin
    insert into books (book_id,
                           book_title,
                           book_author,
                           book_publication_date,
                           book_sales_point,
                           book_summary,
                           book_description,
                           book_price,
                           book_rating,
                           book_publisher)
    values (books_seq.nextval,
                book_info.book_title,
                book_info.book_author,
                book_info.book_publication_date,
                book_info.book_sales_point,
                book_info.book_summary,
                book_info.book_description,
                book_info.book_price,
                book_info.book_rating,
                book_info.book_publisher);
end add_book;

PROCEDURE find_book_by_title(
        p_book_title IN Books.book_title%TYPE,
        p_book OUT SYS_REFCURSOR
    ) AS
BEGIN
    OPEN p_book FOR
        SELECT book_id, book_title, book_author, book_publication_date,
                book_sales_point, book_summary, book_description,
                book_price, book_rating, book_publisher
        FROM Books
        WHERE book_title = p_book_title;
END find_book_by_title;

PROCEDURE delete_book(p_book_id IN Books.book_id%TYPE) AS
BEGIN
    DELETE FROM Books WHERE book_id = p_book_id;
END delete_book;
procedure update_book(
       p_book_id in books.book_id%type,
       book_info in book_info_rec) as
begin
    update books
    set book_title = book_info.book_title,
        book_author = book_info.book_author,
        book_price = book_info.book_price,
        book_description = book_info.book_description,
        book_summary = book_info.book_summary,
        book_publication_date = book_info.book_publication_date,
        book_sales_point = book_info.book_sales_point,
        book_rating = book_info.book_rating
    where book_id = p_book_id;
end update_book;

procedure find_book_by_id(
    p_book_id in books.book_id%type,
    p_books OUT SYS_REFCURSOR
) as
begin
    OPEN p_books FOR
        SELECT book_id, book_title, book_author, book_publication_date,
               book_sales_point, book_summary, book_description,
               book_price, book_rating, book_publisher
        FROM Books
        WHERE book_id = p_book_id;
end find_book_by_id;

procedure find_all_book(
    p_books OUT SYS_REFCURSOR
) as
begin
    OPEN p_books FOR
        SELECT book_id, book_title, book_author, book_publication_date,
               book_sales_point, book_summary, book_description,
               book_price, book_rating, book_publisher
        FROM Books;
end find_all_book;
end book_pkg;
/
