
-- 프로시저의 in, out 모드 -
-- 파라미터는 호출 시 값이 전달되는 읽기 전용 인자다. -> final
-- in 모드는 사용은 가능하지만, 수정할 수는 없는 프로시저의 인자다.
-- out 모드는 프로시저나 함수가 값을 반환하기 위해 사용하는 인자이다.
-- 프로시저나 함수가 작업 결과를 호출자에게 반환할 때 사용한다.
-- ex ) 프로시저 내부에서 전달된 in 인자는 내부에서 사용되기만 한다.
-- -- out 인자는 호출 결과로 리턴받을 수 있다.


create or replace package book_pkg as
    procedure add_book(
        p_book_id in books.book_id%type,
        p_book_title in books.book_title%type,
        p_book_author in books.book_author%type,
        p_book_publication_date in books.book_publication_date%type,
        p_book_summary in books.book_descriptio%type,
        p_book_price in books.book_price%type,
        p_book_rating in books.book_rating%type,
        p_book_publisher in books.book_publisher%type
    );
end book_pkg;
/


