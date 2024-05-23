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

CREATE OR REPLACE PACKAGE Categories_Pkg IS
    PROCEDURE insert_category (
        p_category_name IN Categories.category_name%TYPE,
        p_prior_category_id IN Categories.prior_category_id%TYPE
    );

    PROCEDURE get_category (
        p_category_id IN Categories.category_id%TYPE,
        p_category_name OUT Categories.category_name%TYPE,
        p_prior_category_id OUT Categories.prior_category_id%TYPE
    );


    PROCEDURE update_category (
        p_category_id IN Categories.category_id%TYPE,
        p_category_name IN Categories.category_name%TYPE,
        p_prior_category_id IN Categories.prior_category_id%TYPE
    );

    PROCEDURE delete_category (
        p_category_id IN Categories.category_id%TYPE
    );
END Categories_Pkg;
/

CREATE OR REPLACE PACKAGE BODY Categories_Pkg IS

    PROCEDURE insert_category (
        p_category_name IN Categories.category_name%TYPE,
        p_prior_category_id IN Categories.prior_category_id%TYPE
    ) IS
    BEGIN
        INSERT INTO Categories (category_id, category_name, prior_category_id)
        VALUES (categories_seq.nextval, p_category_name, p_prior_category_id);
        COMMIT;
    END insert_category;

    PROCEDURE get_category (
        p_category_id IN Categories.category_id%TYPE,
        p_category_name OUT Categories.category_name%TYPE,
        p_prior_category_id OUT Categories.prior_category_id%TYPE
    ) IS
    BEGIN
        SELECT category_name, prior_category_id
        INTO p_category_name, p_prior_category_id
        FROM Categories
        WHERE category_id = p_category_id;
    END get_category;

    PROCEDURE update_category (
        p_category_id IN Categories.category_id%TYPE,
        p_category_name IN Categories.category_name%TYPE,
        p_prior_category_id IN Categories.prior_category_id%TYPE
    ) IS
    BEGIN
        UPDATE Categories
        SET category_name = p_category_name,
            prior_category_id = p_prior_category_id
        WHERE category_id = p_category_id;
        COMMIT;
    END update_category;

    PROCEDURE delete_category (
        p_category_id IN Categories.category_id%TYPE
    ) IS
    BEGIN
        DELETE FROM Categories
        WHERE category_id = p_category_id;
        COMMIT;
    END delete_category;

END Categories_Pkg;
/