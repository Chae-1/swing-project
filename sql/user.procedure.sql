create or replace type user_form as object (
    user_name VARCHAR2(30),
    user_password VARCHAR2(30),
    user_log_id VARCHAR2(30),
    user_registration_date timestamp,
    user_image VARCHAR2(30)
);

CREATE OR REPLACE PACKAGE user_pkg AS
    PROCEDURE add_user(p_user_form IN user_form);
    PROCEDURE find_user_by_logid(p_user_log_id IN users.user_log_id%TYPE,
           				 	p_user_password IN users.user_password%TYPE,
            				 	p_user out SYS_REFCURSOR);
    PROCEDURE delete_user_by_id(p_user_id IN users.user_id%TYPE);
    PROCEDURE update_user_password(p_user_id IN users.user_id%TYPE, p_new_password IN users.user_password%TYPE);
    procedure find_user_by_id(
        p_user_id in users.user_id%type,
        p_user out sys_refcursor
    );
END user_pkg;
/



CREATE OR REPLACE PACKAGE BODY user_pkg AS
    PROCEDURE add_user(p_user_form IN user_form) is
    BEGIN
	insert into users(user_id, user_name, user_password,
                  	user_log_id, user_registration_date, user_image)
	values (users_seq.nextval, p_user_form.user_name,
       		p_user_form.user_password, p_user_form.user_log_id,
        	p_user_form.user_registration_date, p_user_form.user_image);
    END add_user;

    PROCEDURE find_user_by_logid(
		p_user_log_id IN users.user_log_id%TYPE,
		p_user_password IN users.user_password%TYPE,
		p_user OUT SYS_REFCURSOR
	) is
    BEGIN
        OPEN p_user FOR
        SELECT u.*
        from users u
        WHERE user_log_id = p_user_log_id AND user_password = p_user_password;
    END find_user_by_logid;

    PROCEDURE delete_user_by_id(p_user_id IN users.user_id%TYPE) is
    BEGIN
        DELETE FROM users
	WHERE user_id = p_user_id;
    	END delete_user_by_id;
    PROCEDURE update_user_password(
		p_user_id IN users.user_id%TYPE,
	 	p_new_password IN users.user_password%TYPE) is
    BEGIN
        UPDATE users
        SET user_password = p_new_password
        WHERE user_id = p_user_id;
    END update_user_password;

    procedure find_user_by_id(
            p_user_id in users.user_id%type,
            p_user out sys_refcursor
    ) as
    begin
        open p_user for
            select *
            from users
            where user_id = p_user_id;
    end find_user_by_id;
END user_pkg;
/