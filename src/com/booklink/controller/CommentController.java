package com.booklink.controller;

import com.booklink.model.book.comments.CommentDto;
import com.booklink.model.book.comments.CommentFormDto;
import com.booklink.service.CommentService;
import java.util.List;

public class CommentController {
    private final CommentService commentService;

    public CommentController() {
        this.commentService = new CommentService();
    }

    public void registerComment(CommentFormDto dto) {
        commentService.registerComment(dto);
    }

    public List<CommentDto> findAllCommentByBookId(Long bookId) {

        return commentService.findAllCommentById(bookId);
    }

    public void removeComment(Long commentId, Long userId) {
        commentService.removeComment(commentId, userId);
    }
}
