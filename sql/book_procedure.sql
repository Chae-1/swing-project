-- 프로시저의 in, out 모드 -
-- 파라미터는 호출 시 값이 전달되는 읽기 전용 인자다. -> final
-- in 모드는 사용은 가능하지만, 수정할 수는 없는 프로시저의 인자다.
-- out 모드는 프로시저나 함수가 값을 반환하기 위해 사용하는 인자이다.
-- 프로시저나 함수가 작업 결과를 호출자에게 반환할 때 사용한다.
-- ex ) 프로시저 내부에서 전달된 in 인자는 내부에서 사용되기만 한다.
-- -- out 인자는 호출 결과로 리턴받을 수 있다.
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

create unique index idx_books on books(book_id);
CREATE INDEX books_title_idx ON books(book_title) INDEXTYPE IS CTXSYS.CONTEXT;
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

drop sequence books_seq;

create sequence books_seq
    start with 1
    increment by 1
    nocycle
    cache 20;

CREATE OR REPLACE TYPE book_info_tab
    AS TABLE OF book_info_rec;


CREATE OR REPLACE TYPE book_rec as OBJECT (
        book_id INTEGER,
        book_info book_info_rec
);


create or replace package book_pkg as
    procedure find_all_book_with_count(
        p_books OUT SYS_REFCURSOR
    );

    procedure add_books(
        p_books in book_info_tab
    );

    procedure add_book(
        book_info in book_info_rec
    );

    procedure find_book_by_title(
        p_book_title in books.book_title%type,
        p_book OUT SYS_REFCURSOR
    );

    procedure find_books_by_cat_name(
        p_category_name in categories.category_name%type,
        p_books OUT SYS_REFCURSOR
    );

    procedure delete_book(
        p_book_id in books.book_id%type
    );

    procedure update_book(
        p_book_id in books.book_id%type,
        book_info in book_info_rec
    );
    procedure find_book_contains_title(
        p_book_title in books.book_title%type,
        p_book out sys_refcursor
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
    procedure add_books(
        p_books in book_info_tab
    ) as
    begin
        for i in 1 .. p_books.COUNT loop
                insert into books
                (book_id, book_title, book_author, book_publication_date, book_sales_point, book_summary, book_description, book_price, book_rating, book_publisher)
                values
                    (books_seq.nextval,
                     p_books(i).book_title,
                     p_books(i).book_author,
                     p_books(i).book_publication_date,
                     p_books(i).book_sales_point,
                     p_books(i).book_summary,
                     p_books(i).book_description,
                     p_books(i).book_price,
                     p_books(i).book_rating,
                     p_books(i).book_publisher);
            end loop;
        commit;
    end add_books;
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
            FROM Books
            where book_id > 0;
    end find_all_book;

    procedure find_all_book_with_count(
        p_books OUT SYS_REFCURSOR
    ) as
    begin
        OPEN p_books FOR
            SELECT book_id, book_title, book_author, book_publication_date,
                   book_sales_point, book_summary, book_description,
                   book_price, book_rating, book_publisher
            FROM Books
            where book_id > 0;
    end find_all_book_with_count;
    procedure find_book_contains_title(
        p_book_title in books.book_title%type,
        p_book out sys_refcursor
    ) as
    begin
        open p_book for
            SELECT book_id, book_title, book_author, book_publication_date,
                   book_sales_point, book_summary, book_description,
                   book_price, book_rating, book_publisher
            FROM Books
            where contains(book_title, p_book_title, 1) > 1;
    end find_book_contains_title;
PROCEDURE find_books_by_cat_name(
    p_category_name IN categories.category_name%TYPE,
    p_books OUT SYS_REFCURSOR
) IS
    v_sql VARCHAR2(4000);
BEGIN
    v_sql := 'SELECT b.book_id, b.book_title, b.book_author, b.book_publication_date, ' ||
             'b.book_sales_point, b.book_summary, b.book_description, ' ||
             'b.book_price, b.book_rating, b.book_publisher ' ||
             'FROM (SELECT b.*, ROW_NUMBER() OVER(PARTITION BY b.book_id ORDER BY b.book_title) AS rn ' ||
             'FROM books b ' ||
             'JOIN bookcategories bc ON b.book_id = bc.book_id ' ||
             'JOIN categories c ON c.category_id = bc.category_id';

    IF p_category_name != '전체' THEN
        v_sql := v_sql || ' WHERE c.category_name = :category_name';
    END IF;
    v_sql := v_sql || ') ' || 'WHERE rn = 1';

    OPEN p_books FOR v_sql USING p_category_name;
END find_books_by_cat_name;
end book_pkg;
/
