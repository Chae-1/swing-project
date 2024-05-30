package com.booklink.service;

import com.booklink.dao.CommentDao;
import com.booklink.dao.OrderDao;
import com.booklink.model.book.comments.CommentSummaryDto;
import com.booklink.model.book.comments.CommentFormDto;
import com.booklink.model.book.comments.Comments;
import com.booklink.model.book.comments.exception.CommentException;
import com.booklink.model.book.comments.exception.CommentExistException;
import com.booklink.model.order.OrderCount;
import com.booklink.model.user.exception.UserNotFoundException;
import com.booklink.utils.UserHolder;

import java.util.List;
import java.util.Optional;

public class CommentService {
    private final OrderDao orderDao;
    private final CommentDao commentDao;

    public CommentService() {
        this.commentDao = new CommentDao();
        this.orderDao = new OrderDao();
    }

    public void registerComment(CommentFormDto dto) {
        if (!UserHolder.isLogin()) {
            throw new UserNotFoundException();
        }
        if (isExistComment(dto.userId(), dto.bookId())) {
            throw new CommentExistException();
        }
        OrderCount orderCount = orderDao.findOrderCountAboutBook(dto.userId(), dto.bookId());
        commentDao.registerComment(dto, orderCount.isPurchased() ? "Y" : "N");
    }

    private boolean isExistComment(Long userId, Long bookId) {
        Optional<Comments> userCommentOnBook = commentDao.findUserCommentOnBook(userId, bookId);
        System.out.println(userCommentOnBook.isPresent());
        if (userCommentOnBook.isPresent()) {
            return true;
        }
        return false;
    }

    public void removeComment(Long commentId, Long userId) {
        // 해당 commentId의 댓글 userId와 로그인한 user의 userId와 같으면 삭제

        commentDao.findCommentById(commentId, userId)
                .ifPresentOrElse((comments) -> {
                    commentDao.removeComment(commentId, userId);
                }, () -> {
                    throw new CommentException("삭제할 수 있는 권한이 없습니다.");
                });
    }

    public List<CommentSummaryDto> findAllCommentById(Long bookId) {
        return commentDao.findAllCommentByBookId(bookId);
    }

}
