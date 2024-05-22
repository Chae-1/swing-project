-- 프로시저의 in, out 모드 -
-- 파라미터는 호출 시 값이 전달되는 읽기 전용 인자다. -> final
-- in 모드는 사용은 가능하지만, 수정할 수는 없는 프로시저의 인자다.
-- out 모드는 프로시저나 함수가 값을 반환하기 위해 사용하는 인자이다.
-- 프로시저나 함수가 작업 결과를 호출자에게 반환할 때 사용한다.
-- ex ) 프로시저 내부에서 전달된 in 인자는 내부에서 사용되기만 한다.
-- -- out 인자는 호출 결과로 리턴받을 수 있다.
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
        p_book out book_rec
    );

    procedure delete_book(
       book_id in books.book_id%type
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
        p_book OUT book_rec
    ) AS
BEGIN
        dbms_output.put_line(p_book_title);
    DECLARE
        v_book_id Books.book_id%TYPE;
        v_book_info book_info_rec := book_info_rec(NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
    BEGIN
        BEGIN
            SELECT book_id, book_title, book_author, book_publication_date,
                   book_sales_point, book_summary, book_description,
                   book_price, book_rating, book_publisher
            INTO v_book_id, v_book_info.book_title, v_book_info.book_author,
                v_book_info.book_publication_date, v_book_info.book_sales_point,
                v_book_info.book_summary, v_book_info.book_description,
                v_book_info.book_price, v_book_info.book_rating,
                v_book_info.book_publisher
            FROM Books
            WHERE book_title = p_book_title;
            p_book := book_rec(v_book_id, v_book_info);
            dbms_output.put_line(v_book_id);
        EXCEPTION
            WHEN NO_DATA_FOUND THEN
                p_book := book_rec(NULL, book_info_rec(NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL));
                DBMS_OUTPUT.PUT_LINE('No book found with the given title.');
        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
        END;
    END;
END find_book_by_title;

PROCEDURE delete_book(p_book_id IN Books.book_id%TYPE) AS
BEGIN
    DELETE FROM Books WHERE book_id = p_book_id;
END delete_book;

end book_pkg;
/
