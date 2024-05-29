package com.booklink.controller;

import com.booklink.dao.BookDiscussionDao;
import com.booklink.dao.OrderDao;
import com.booklink.model.book.disscussion.BookDiscussion;
import com.booklink.model.book.disscussion.BookDiscussionDto;
import com.booklink.model.book.disscussion.BookDiscussionRegisterForm;
import com.booklink.model.order.OrderCount;
import com.booklink.model.user.exception.UserPermissionException;
import com.booklink.utils.UserHolder;

import java.util.List;

public class BookDiscussionController {
    private BookDiscussionDao dao;
    private OrderDao orderDao;

    public BookDiscussionController() {
        this.dao = new BookDiscussionDao();
        this.orderDao = new OrderDao();
    }

    public List<BookDiscussionDto> findAllDiscussionAboutBook(Long bookId) {
        OrderCount orderCountAboutBook = orderDao.findOrderCountAboutBook(UserHolder.getId(), bookId);
        if (!orderCountAboutBook.isPurchased()) {
            throw new UserPermissionException();
        }
        return dao.findAllDiscussionAboundBook(bookId);
    }

    public void addDiscussion(BookDiscussionRegisterForm form) {
        dao.registerDiscussion(form);
    }
}
