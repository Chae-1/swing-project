package com.booklink.model.book.exception;

public class BookAlreadyExistException extends BookException {
    public BookAlreadyExistException() {
        super("이미 존재하는 서적입니다.");
    }
}
