package com.booklink.model.book.exception;

public class BookNotExistException extends BookException {
    public BookNotExistException() {
        super("존재하지 않는 도서입니다.");
    }
}
