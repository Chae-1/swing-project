package com.booklink.model.user.exception;

public class UserPermissionException extends UserException{

    public UserPermissionException() {
        super("작성 권한이 존재하지 않습니다.");
    }
}
