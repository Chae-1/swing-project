package com.booklink.controller;

import com.booklink.dao.BookDiscussionDao;
import com.booklink.dao.OrderDao;
import com.booklink.model.book.disscussion.BookDiscussion;
import com.booklink.model.book.disscussion.BookDiscussionDto;
import com.booklink.model.book.disscussion.BookDiscussionRegisterForm;
import com.booklink.model.order.OrderCount;
import com.booklink.model.user.exception.UserPermissionException;
import com.booklink.service.BookDiscussionService;
import com.booklink.utils.UserHolder;

import java.util.List;

public class BookDiscussionController {
    private BookDiscussionService service;

    public BookDiscussionController() {
        this.service = new BookDiscussionService();
    }

    public List<BookDiscussionDto> findAllDiscussionAboutBook(Long bookId) {
        return service.findAllDiscussionAboutBook(bookId);
    }

    public void addDiscussion(BookDiscussionRegisterForm form) {
        service.addDiscussion(form);
    }
}
