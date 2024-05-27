package com.booklink.model.book.comments.exception;

import com.booklink.model.user.exception.UserException;

public class CommentException extends UserException {
    public CommentException(String message) {
        super(message);
    }
}
