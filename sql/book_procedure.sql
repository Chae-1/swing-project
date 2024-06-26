CREATE OR REPLACE TYPE book_info_rec AS OBJECT
(
    book_title            VARCHAR2(50),
    book_author           VARCHAR2(30),
    book_publication_date DATE,
    book_sales_point      INTEGER,
    book_summary          CLOB,
    book_description      CLOB,
    book_price            INTEGER,
    book_rating           NUMBER(2, 1),
    book_publisher        VARCHAR2(50),
    book_image_url varchar2(3000)
);

create or replace type book_register_info as object
(
    book_title            VARCHAR2(50),
    book_author           VARCHAR2(30),
    book_publication_date DATE,
    book_summary          CLOB,
    book_description      CLOB,
    book_price            INTEGER,
    book_publisher        VARCHAR2(50),
    book_image_url        varchar2(3000)
);

drop sequence books_seq;

create sequence books_seq
    start with 1
    increment by 1
    nocycle
    cache 20;

CREATE OR REPLACE TYPE book_info_tab
AS TABLE OF book_info_rec;


CREATE OR REPLACE TYPE book_rec as OBJECT
(
    book_id   INTEGER,
    book_info book_info_rec
);

-- 도서 등록시 전달받은 카테고리 목록
CREATE OR REPLACE TYPE CATEGORY_NAME_ARRAY AS TABLE OF VARCHAR2(255);/

create or replace package book_pkg as
    procedure find_all_book_with_count(
        p_books OUT SYS_REFCURSOR
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

    procedure add_book_with_categories(
        p_book_categories_info in book_register_info,
        input_categories IN CATEGORY_NAME_ARRAY
    );

    PROCEDURE update_book_with_categories(
        p_book_categories_info IN book_register_info,
        p_book_id IN books.book_id%TYPE,
        prev_categories IN CATEGORY_NAME_ARRAY,
        curr_categories in category_name_array
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
                           book_publisher,
                           book_image_url)
        values (books_seq.nextval,
                book_info.book_title,
                book_info.book_author,
                book_info.book_publication_date,
                book_info.book_sales_point,
                book_info.book_summary,
                book_info.book_description,
                book_info.book_price,
                book_info.book_rating,
                book_info.book_publisher,
                book_info.book_image_url);
    end add_book;

    PROCEDURE find_book_by_title(
        p_book_title IN Books.book_title%TYPE,
        p_book OUT SYS_REFCURSOR
    ) AS
    BEGIN
        OPEN p_book FOR
            SELECT book_id,
                   book_title,
                   book_author,
                   book_publication_date,
                   book_sales_point,
                   book_summary,
                   book_description,
                   book_price,
                   book_rating,
                   book_publisher,
                   book_image_url
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
        set book_title            = book_info.book_title,
            book_author           = book_info.book_author,
            book_price            = book_info.book_price,
            book_description      = book_info.book_description,
            book_summary          = book_info.book_summary,
            book_publication_date = book_info.book_publication_date,
            book_sales_point      = book_info.book_sales_point,
            book_rating           = book_info.book_rating
        where book_id = p_book_id;
    end update_book;

    procedure find_book_by_id(
        p_book_id in books.book_id%type,
        p_books OUT SYS_REFCURSOR
    ) as
    begin
        OPEN p_books FOR
            SELECT book_id,
                   book_title,
                   book_author,
                   book_publication_date,
                   book_sales_point,
                   book_summary,
                   book_description,
                   book_price,
                   book_rating,
                   book_publisher,
                   book_image_url
            FROM Books
            WHERE book_id = p_book_id;
    end find_book_by_id;

    procedure find_all_book(
        p_books OUT SYS_REFCURSOR
    ) as
    begin
        OPEN p_books FOR
            select *
            from books_all;
    end find_all_book;

    procedure find_all_book_with_count(
        p_books OUT SYS_REFCURSOR
    ) as
    begin
        OPEN p_books FOR
            SELECT book_id,
                   book_title,
                   book_author,
                   book_publication_date,
                   book_sales_point,
                   book_summary,
                   book_description,
                   book_price,
                   book_rating,
                   book_publisher,
                   book_image_url
            FROM Books
            where book_id > 0;
    end find_all_book_with_count;

    procedure find_book_contains_title(
        p_book_title in books.book_title%type,
        p_book out sys_refcursor
    ) as
    begin
        open p_book for
            SELECT book_id,
                   book_title,
                   book_author,
                   book_publication_date,
                   book_sales_point,
                   book_summary,
                   book_description,
                   book_price,
                   book_rating,
                   book_publisher,
                   book_image_url
            FROM Books
            where book_title like '%' || p_book_title || '%';
    end find_book_contains_title;

    PROCEDURE find_books_by_cat_name(
        p_category_name IN categories.category_name%TYPE,
        p_books OUT SYS_REFCURSOR
    ) IS
    BEGIN
        IF p_category_name != '전체' THEN
            OPEN p_books FOR
                with specified_categories
                         as(
                        select category_id, category_name
                        from categories
                        start with category_id in (
                            select category_id
                            from categories c
                            where c.category_name = p_category_name
                        )
                        connect by prior category_id = prior_category_id
                    ), result as
                         (select b.*, row_number() over(partition by b.book_id order by b.book_id) as rn
                          from books b
                                   join bookcategories bc on b.book_id = bc.book_id
                                   join specified_categories sc on sc.category_id = bc.category_id
                          where b.book_id > 0)
                select * from result
                where rn = 1;
        ELSE
            OPEN p_books FOR
                SELECT book_id,
                       book_title,
                       book_author,
                       book_publication_date,
                       book_sales_point,
                       book_summary,
                       book_description,
                       book_price,
                       book_rating,
                       book_publisher,
                       book_image_url
                FROM Books;
        END IF;
    END find_books_by_cat_name;

    procedure add_book_with_categories(
        p_book_categories_info in book_register_info,
        input_categories        IN CATEGORY_NAME_ARRAY
    ) as
        p_book_id books.book_id%type;
    begin
        insert into books
        (book_id, book_title, book_author, book_publication_date, book_sales_point, book_summary,
         book_description, book_price, book_rating, book_publisher, book_image_url)
        values (books_seq.nextval,
                p_book_categories_info.book_title,
                p_book_categories_info.book_author,
                p_book_categories_info.book_publication_date,
                0,
                p_book_categories_info.book_summary,
                p_book_categories_info.book_description,
                p_book_categories_info.book_price,
                0,
                p_book_categories_info.book_publisher,
                p_book_categories_info.book_image_url);
        p_book_id := books_seq.currval;

        for i in 1 .. input_categories.count loop
                insert into bookcategories
                values ((select category_id
                         from categories
                         where category_name = input_categories(i)), p_book_id, book_categories_seq.nextval);
            end loop;
    end add_book_with_categories;

    procedure update_book_with_categories(
        p_book_categories_info in book_register_info,
        p_book_id in books.book_id%type,
        prev_categories        IN CATEGORY_NAME_ARRAY,
        curr_categories in CATEGORY_NAME_ARRAY
    ) as
        p_idx int;
        prev_category_id NUMBER;
        curr_category_id NUMBER;
    begin
        p_idx := 1;
        update books
        set book_title            = p_book_categories_info.book_title,
            book_author           = p_book_categories_info.book_author,
            book_price            = p_book_categories_info.book_price,
            book_description      = p_book_categories_info.book_description,
            book_summary          = p_book_categories_info.book_summary,
            book_publication_date = p_book_categories_info.book_publication_date,
            book_image_url = p_book_categories_info.book_image_url,
            book_publisher = p_book_categories_info.book_publisher
        where book_id = p_book_id;

        FOR i IN 1 .. prev_categories.COUNT LOOP

             prev_category_id := find_category_id_by_name(prev_categories(i));
             curr_category_id := find_category_id_by_name(curr_categories(i));
             update BookCategories
             set category_id = curr_category_id
             where book_id = p_book_id and category_id = prev_category_id;
                p_idx := i;
        END LOOP;

        if p_idx != 2 then
        for i in p_idx .. curr_categories.count loop
                curr_category_id := find_category_id_by_name(curr_categories(i));
                insert into BookCategories
                values (book_categories_seq.nextval, curr_category_id, p_book_id);
        end loop;
        end if;
    end;
end book_pkg;
/

create or replace function find_category_id_by_name(f_category_name in varchar2(200)) return integer is
    f_category_id integer;
begin
    SELECT category_id into f_category_id
    INTO category_id
    FROM Categories
    WHERE category_name = f_category_name;

    RETURN f_category_id;
end
/

create or replace view books_all as
SELECT book_id,
                   book_title,
                   book_author,
                   book_publication_date,
                   book_sales_point,
                   book_summary,
                   book_description,
                   book_price,
                   book_rating,
                   book_publisher,
                   book_image_url
            FROM Books
            where book_id > 0;
