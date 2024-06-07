package com.booklink.model.user.exception;

public class UserNotFoundException extends UserException {
    private static final String MESSAGE = "유저 정보를 찾을 수 없습니다.";
    public UserNotFoundException() {
        super(MESSAGE);
    }
}
