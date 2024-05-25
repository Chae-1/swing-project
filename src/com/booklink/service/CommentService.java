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
        int orderCount = orderDao.findOrderCount(dto.bookId(), dto.userId());
        commentDao.registerComment(dto, orderCount > 0 ? "Y" : "N");
    }

    public List<CommentDto> findAllCommentById(Long bookId) {
        return commentDao.findAllCommentById(bookId);
    }
}
