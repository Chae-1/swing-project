package com.booklink.controller;

import com.booklink.model.book.Book;
import com.booklink.model.book.BookRegisterDto;
import com.booklink.service.BookService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class BookController {
    private final BookService bookService;

    public BookController() {
        this.bookService = new BookService();
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


    public void registerBookWithCategories(BookRegisterDto dto, List<String> inputCategories) {
        String title = dto.title();
        // 예외가 발생하면 예외의 내용을 출력해서 보여준다.
        bookService.findBookByTitle(title);
        bookService.registerBookWithCategories(dto, inputCategories);

    }

    public void removeBookById(Long id) {
        bookService.deleteBookById(id);
    }

    public void updateBookWithCategories(BookRegisterDto dto, Long id, List<String> updatedCategories) {
        bookService.updateBookWithCategories(dto, id, updatedCategories);
    }

    public Optional<Book> findByBookId(Long bookId) {
        return bookService.findBookById(bookId);
    }
}
