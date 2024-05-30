package com.booklink.service;

import com.booklink.dao.BookDao;
import com.booklink.dao.BookDiscussionDao;
import com.booklink.dao.OrderDao;
import com.booklink.model.book.disscussion.BookDiscussionDto;
import com.booklink.model.book.disscussion.BookDiscussionRegisterForm;
import com.booklink.model.order.OrderCount;
import com.booklink.model.user.exception.UserPermissionException;
import com.booklink.utils.UserHolder;

import java.util.List;

public class BookDiscussionService {
    private final BookDiscussionDao dao;
    private final OrderDao orderDao;

    public BookDiscussionService() {
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
        OrderCount orderCountAboutBook = orderDao.findOrderCountAboutBook(UserHolder.getId(), form.bookId());
        if (!orderCountAboutBook.isPurchased()) {
            throw new UserPermissionException();
        }
        dao.registerDiscussion(form);
    }
}
