package com.booklink.controller;

import com.booklink.model.book.disscussion.comment.DiscussionCommentDto;
import com.booklink.model.book.disscussion.comment.DiscussionCommentRegisterDto;
import com.booklink.service.DiscussionCommentService;

import java.util.List;

public class DiscussionCommentController {
    private DiscussionCommentService service;

    public DiscussionCommentController() {
        this.service = new DiscussionCommentService();
    }

    public List<DiscussionCommentDto> findAllDiscCommentByDiscId(Long discId) {
        return service.findAllDiscCommentByDiscId(discId);
    }

    public List<DiscussionCommentDto> findAllCommentByDiscussionId(Long discussionId) {
        return service.findAllDiscCommentByDiscId(discussionId);
    }

    public void removeComment(Long commentId, Long userId) {
        service.removeComment(commentId, userId);
    }

    public void registerComment(DiscussionCommentRegisterDto discussionCommentDto) {
        service.registerDiscComment(discussionCommentDto);
    }
}
