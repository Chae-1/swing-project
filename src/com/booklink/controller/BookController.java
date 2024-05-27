package com.booklink.controller;

import com.booklink.dao.BookDao;
import com.booklink.model.book.Book;
import com.booklink.model.book.BookListWithCount;
import com.booklink.model.book.BookRegisterDto;
import com.booklink.model.user.User;
import com.booklink.service.BookService;
import com.booklink.utils.UserHolder;

import java.util.List;

public class BookController {
    private final BookService bookService;

    public BookController() {
        this.bookService = new BookService(new BookDao());
    }

    public List<Book> findAllBookWithCount() {
        return bookService.findAllBookWithCount();
    }

    public List<Book> findBooksByContainsTitle(String title) {
        return bookService.findBooksContainsTitle(title);
    }

    public List<Book> findBooksByContainsCategoryName(String categoryName) {
        return bookService.findBookByCategoryName(categoryName);
    }

    public void registerBookWithCategories(BookRegisterDto dto) {
        String title = dto.title();
        // 예외가 발생하면 예외의 내용을 출력해서 보여준다.
        bookService.findBookByTitle(title);
        bookService.registerBookWithCategories(dto);

    }

    public void removeBookById(Long id) {
        bookService.deleteBookById(id);
    }
}
