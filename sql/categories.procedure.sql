CREATE OR REPLACE PACKAGE Categories_Pkg IS
    procedure insert_maincategory(
        p_category_name in Categories.category_name%type
    );

    procedure insert_subcategory(
        p_category_name in Categories.category_name%type,
        p_prior_id in Categories.prior_category_id%type
    );

    PROCEDURE find_parent_category(
        p_category_name in Categories.category_name%TYPE,
        p_category out sys_refcursor
    );
    procedure find_all_categories_of_book(
        p_book_id in books.book_id%type,
        p_category out sys_refcursor
    );

    procedure find_all_with_level(
        p_category out sys_refcursor
    );

    procedure find_all_simple_category(
        p_book_id in books.book_id%type,
        p_category out sys_refcursor
    );

    procedure find_all(
        p_category out sys_refcursor
    );

    procedure find_book_category_names(
        category_name_array in CATEGORY_NAME_ARRAY,
        p_category out sys_refcursor
    );

END Categories_Pkg;
/

CREATE OR REPLACE PACKAGE BODY Categories_Pkg IS

    procedure insert_maincategory(
        p_category_name in Categories.category_name%type
    ) as
    begin
        insert into Categories(category_id, category_name, prior_category_id)
        values (categories_seq.nextval, p_category_name, null);
    end insert_maincategory;

    procedure find_all_with_level(
        p_category out sys_refcursor
    ) as
    begin
        open p_category for
            with temp as (select category_id, category_name, prior_category_id, level as t_depth
                          from Categories
                          start with prior_category_id is null
                          connect by prior category_id = prior_category_id)
            select category_id, category_name, prior_category_id, t_depth
            from temp;
    end find_all_with_level;

    procedure find_all_categories_of_book(
        p_book_id in books.book_id%type,
        p_category out sys_refcursor
    ) as
    begin
        open p_category for
            select case
                       when prior_category_id is not null then (select category_name
                                                                from categories c2
                                                                where c2.category_id = c.prior_category_id) || '->' || category_name
                       else category_name
                       end as category_path
            from categories c
            where c.category_id in (select bc.category_id
                                    from bookcategories bc
                                    where book_id = p_book_id);
    end find_all_categories_of_book;

    procedure insert_subcategory(
        p_category_name in Categories.category_name%type,
        p_prior_id in Categories.prior_category_id%type
    ) as
    begin
        insert into Categories(category_id, category_name, prior_category_id)
        values (categories_seq.nextval, p_category_name, p_prior_id);
    end insert_subcategory;

    PROCEDURE find_parent_category(
        p_category_name in Categories.category_name%TYPE,
        p_category out sys_refcursor
    ) as
    begin
        open p_category for
            select category_id, category_name, prior_category_id
            from Categories
            where category_name = p_category_name;
    END find_parent_category;

    procedure find_all_simple_category(
        p_book_id in books.book_id%type,
        p_category out sys_refcursor
    ) as
    begin
        open p_category for
            select category_name
            from Categories c
                     join bookcategories bc on c.category_id = bc.category_id
            where bc.book_id = p_book_id;
    end find_all_simple_category;
    procedure find_all(
            p_category out sys_refcursor
    ) as
    begin
        open p_category for
            select *
            from categories;
    end find_all;

    procedure find_book_category_names(
        category_name_array in CATEGORY_NAME_ARRAY,
        p_category out sys_refcursor
    ) as
    begin
        open p_category for
        SELECT b.*
                FROM books b
                JOIN bookcategories bc ON b.book_id = bc.book_id
                JOIN categories c ON bc.category_id = c.category_id
                WHERE c.category_name IN (SELECT COLUMN_VALUE FROM TABLE(category_name_array));
    end;
END Categories_Pkg;
/