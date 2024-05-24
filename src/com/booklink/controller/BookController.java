package com.booklink.controller;

import com.booklink.dao.BookDao;
import com.booklink.model.book.BookListWithCount;
import com.booklink.service.BookService;

public class BookController {
    private final BookService bookService;

    public BookController() {
        this.bookService = new BookService(new BookDao());
    }

    public BookListWithCount findAllBookWithCount() {
        return bookService.findAllBookWithCount();
    }

}
