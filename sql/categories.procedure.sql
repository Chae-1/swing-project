create sequence categories_seq
    start with 1
    increment by 1
    nocycle
cache 20;

CREATE TABLE Categories (
                            category_id INTEGER,
                            category_name VARCHAR2(50),
                            prior_category_id INTEGER
);
create unique index idx_categories on Categories(category_id);
alter table categories add constraint category_pk primary key(category_id);
alter table categories add constraint category_name_nn check(category_name is not null);
alter table categories add constraint prior_category_fk foreign key (prior_category_id) references categories(category_id) on delete cascade;

CREATE OR REPLACE PACKAGE Categories_Pkg IS
    procedure insert_maincategory(
        p_category_name in Categories.category_name%type
    );

    procedure insert_subcategory(
        p_category_name in Categories.category_name%type,
        p_prior_id in Categories.prior_category_id%type
    );

    PROCEDURE find_parent_category (
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
            with temp as(select category_id, category_name, prior_category_id, level as t_depth
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
            with book_category as(
                 select b.book_id,
                       case
                           when level = 1 then c.category_name
                           else substr(SYS_CONNECT_BY_PATH(c.category_name, ' -> '), 5)
                           end AS category_path
                from bookcategories bc
                         join books b on bc.book_id = b.book_id
                         join categories c on bc.category_id = c.category_id
                start with c.prior_category_id is null
                connect by prior c.category_id = c.prior_category_id
            )
            select category_path
            from book_category
            where book_id = p_book_id;
    end;

    procedure insert_subcategory(
        p_category_name in Categories.category_name%type,
        p_prior_id in Categories.prior_category_id%type
    ) as
    begin
        insert into Categories(category_id, category_name, prior_category_id)
        values (categories_seq.nextval, p_category_name, p_prior_id);
    end insert_subcategory;

    PROCEDURE find_parent_category (
        p_category_name in Categories.category_name%TYPE,
        p_category out sys_refcursor
    ) as
    begin
        open p_category for
            select category_id, category_name, prior_category_id
            from Categories
            where category_name = p_category_name;
    END find_parent_category;

END Categories_Pkg;
/