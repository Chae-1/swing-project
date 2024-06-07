package com.booklink.service;

import com.booklink.dao.DiscussionCommentDao;
import com.booklink.model.book.comments.exception.CommentException;
import com.booklink.model.book.disscussion.comment.DiscussionCommentDto;
import com.booklink.model.book.disscussion.comment.DiscussionCommentRegisterDto;

import java.util.List;

public class DiscussionCommentService {
    private DiscussionCommentDao dao;

    public DiscussionCommentService() {
        this.dao = new DiscussionCommentDao();
    }

    public void registerDiscComment(DiscussionCommentRegisterDto discussionCommentDto) {
        dao.registerDiscComment(discussionCommentDto);
    }

    public List<DiscussionCommentDto> findAllDiscCommentByDiscId(Long discId) {
        return dao.findAllDiscCommentByDiscId(discId);
    }

    // 해당 commentId가 user가 작성한 것인가?
    public void removeComment(Long commentId, Long userId) {
        dao.findByCommentId(commentId)
                .ifPresent((dto) -> {
                    if (dto.userId() != userId) {
                        throw new CommentException("삭제 불가");
                    }
                    dao.removeCommentById(commentId);
                });
    }
}
