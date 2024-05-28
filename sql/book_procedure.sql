-- 프로시저의 in, out 모드 -
-- 파라미터는 호출 시 값이 전달되는 읽기 전용 인자다. -> final
-- in 모드는 사용은 가능하지만, 수정할 수는 없는 프로시저의 인자다.
-- out 모드는 프로시저나 함수가 값을 반환하기 위해 사용하는 인자이다.
-- 프로시저나 함수가 작업 결과를 호출자에게 반환할 때 사용한다.
-- ex ) 프로시저 내부에서 전달된 in 인자는 내부에서 사용되기만 한다.
-- -- out 인자는 호출 결과로 리턴받을 수 있다.
CREATE TABLE Books
(
    book_id               INTEGER,
    book_title            VARCHAR2(50),
    book_author           VARCHAR2(30),
    book_publication_date DATE,
    book_sales_point      INTEGER,
    book_summary          CLOB,
    book_description      CLOB,
    book_price            INTEGER,
    book_rating           number(2, 1),
    book_publisher        VARCHAR2(50),
    book_image_url        varchar2(300)
);

create unique index idx_books on books (book_id);
CREATE INDEX books_title_idx ON books (book_title) INDEXTYPE IS CTXSYS.CONTEXT;
alter table books
    add constraint books_pk primary key (book_id);
alter table books
    add constraint books_title_nn check (book_title is not null);
alter table books
    add constraint books_author_nn check (book_author is not null);


CREATE TABLE Categories
(
    category_id       INTEGER,
    category_name     VARCHAR2(50),
    prior_category_id INTEGER
);

CREATE TABLE BookCategories
(
    category_id INTEGER,
    book_id     INTEGER
);

create unique index idx_book_categories on bookcategories (book_id, category_id);
alter table bookcategories
    add constraint bookcategories_pk primary key (book_id, category_id);
alter table bookcategories
    add constraint bookcategories_category_fk foreign key (category_id) references categories (category_id) on delete cascade;
alter table bookcategories
    add constraint bookcategories_book_fk foreign key (book_id) references books (book_id) on delete cascade;

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
    book_publisher        VARCHAR2(50)
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
    book_image_url        varchar2(200)
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

CREATE OR REPLACE TYPE CATEGORY_NAME_ARRAY AS TABLE OF VARCHAR2(255);/

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

    procedure add_book_with_categories(
        p_book_categories_info in book_register_info,
        input_categories        IN CATEGORY_NAME_ARRAY
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
    procedure add_books(
        p_books in book_info_tab
    ) as
    begin
        for i in 1 .. p_books.COUNT
            loop
                insert into books
                (book_id, book_title, book_author, book_publication_date, book_sales_point, book_summary,
                 book_description, book_price, book_rating, book_publisher)
                values (books_seq.nextval,
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
            SELECT book_id,
                   book_title,
                   book_author,
                   book_publication_date,
                   book_sales_point,
                   book_summary,
                   book_description,
                   book_price,
                   book_rating,
                   book_publisher
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
                   book_publisher
            FROM Books
            WHERE book_id = p_book_id;
    end find_book_by_id;

    procedure find_all_book(
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
                   book_publisher
            FROM Books
            where book_id > 0;
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
                   book_publisher
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
                   book_publisher
            FROM Books
            where book_title like '%title%';
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
                       book_publisher
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
         book_description, book_price, book_rating, book_publisher)
        values (books_seq.nextval,
                p_book_categories_info.book_title,
                p_book_categories_info.book_author,
                p_book_categories_info.book_publication_date,
                0,
                p_book_categories_info.book_summary,
                p_book_categories_info.book_description,
                p_book_categories_info.book_price,
                0,
                p_book_categories_info.book_publisher);
        p_book_id := books_seq.currval;

        for i in 1 .. input_categories.count loop
                insert into bookcategories
                values ((select category_id
                         from categories
                         where category_name = input_categories(i)), p_book_id);
        end loop;
    end add_book_with_categories;
    procedure update_book_with_categories(
        p_book_categories_info in book_register_info,
        p_book_id books.book_id%type,
        prev_categories        IN CATEGORY_NAME_ARRAY,
        curr_categories in CATEGORY_NAME_ARRAY
    ) as
        p_idx int;
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
                update BookCategories
                set category_id =
                (
                    select category_id
                    from categories
                    where category_name = curr_categories(i)
                )
                where book_id = p_book_id and category_id = (
                                                            select category_id
                                                            from Categories
                                                            where category_name = prev_categories(i)
                                                            );
                p_idx := i;
        END LOOP;

        for i in p_idx .. curr_categories.count loop
                insert into BookCategories
                values ((select category_id
                           from categories
                           where category_name = curr_categories(i)), p_book_id);
        end loop;
    end;
end book_pkg;
/


