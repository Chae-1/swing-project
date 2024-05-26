package com.booklink.service;

import com.booklink.dao.CommentDao;
import com.booklink.dao.OrderDao;
import com.booklink.dao.UserDao;
import com.booklink.model.book.comments.CommentDto;
import com.booklink.model.book.comments.CommentFormDto;
import com.booklink.model.book.comments.exception.CommentException;
import java.util.List;

public class CommentService {
    private final UserDao userDao;
    private final OrderDao orderDao;
    private final CommentDao commentDao;

    public CommentService() {
        this.userDao = new UserDao();
        this.commentDao = new CommentDao();
        this.orderDao = new OrderDao();
    }

    public void registerComment(CommentFormDto dto) {
        if (dto.userId() == -1) {
            throw new CommentException("로그인 부터 우선적으로 해주세요");
        }
        int orderCount = orderDao.findOrderCountAboutBook(dto.bookId(), dto.userId());
        commentDao.registerComment(dto, orderCount > 0 ? "Y" : "N");
    }

    public void removeComment(Long commentId, Long userId) {
        // 해당 commentId의 댓글 userId와 로그인한 user의 userId와 같으면 삭제
        commentDao.findCommentById(commentId, userId)
                .ifPresentOrElse((comments) -> {
                    commentDao.removeComment(commentId);
                }, () -> {
                    throw new CommentException("삭제할 수 있는 권한이 없습니다.");
                });
    }

    public List<CommentDto> findAllCommentById(Long bookId) {
        return commentDao.findAllCommentByBookId(bookId);
    }
}
