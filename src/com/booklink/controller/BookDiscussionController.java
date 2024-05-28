package com.booklink.controller;

import com.booklink.dao.BookDiscussionDao;
import com.booklink.model.book.disscussion.BookDiscussion;
import com.booklink.model.book.disscussion.BookDiscussionRegisterForm;

import java.util.List;

public class BookDiscussionController {
    private BookDiscussionDao dao;

    public BookDiscussionController() {
        this.dao = new BookDiscussionDao();
    }

    public List<BookDiscussion> findAllDiscussionAboundBook(Long bookId) {
        return dao.findAllDiscussionAboundBook(bookId);
    }

    public void addDiscussion(BookDiscussionRegisterForm form) {
        dao.registerDiscussion(form);
    }
}
