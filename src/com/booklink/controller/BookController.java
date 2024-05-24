package com.booklink.controller;

import com.booklink.dao.BookDao;
import com.booklink.model.book.Book;
import com.booklink.service.BookService;

import java.util.List;

public class BookController {
    private final BookService bookService;

    public BookController() {
        this.bookService = new BookService(new BookDao());
    }

    public List<Book> findAllBookWithCount() {
        return bookService.findAllBookWithCount();
    }

}
