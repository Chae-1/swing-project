package com.booklink.model.book.comments.exception;

public class CommentExistException extends CommentException{
    public CommentExistException() {
        super("이미 댓글을 작성 했습니다.");
    }
}
