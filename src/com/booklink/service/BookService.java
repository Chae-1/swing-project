package com.booklink.service;

import com.booklink.dao.BookDao;
import com.booklink.dao.CategoriesDao;
import com.booklink.model.book.Book;
import com.booklink.model.book.BookDto;

import com.booklink.model.book.BookRegisterDto;
import com.booklink.utils.UserHolder;

import java.util.List;
import java.util.Optional;

public class BookService {
    private final BookDao bookDao;

    private final CategoriesDao categoriesDao;

    public BookService() {
        this.bookDao = new BookDao();
        this.categoriesDao = new CategoriesDao();
    }

    public void registerBook(BookDto bookDto) {
        // 1. 책의 이름으로 조회한다.
        // 2. 기존에 등록되어 있는 책이라면 예외를 발생시킨다.
        // 3. 그렇지 않다면 등록을 정상적으로 수행할 수 있다.
        Optional<Book> findBook = bookDao.findBookByTitle(bookDto.title());
        findBook.ifPresent((book) -> {
            // Exception 객체 만들 예정
            // -> 이미 존재하는 서적
            // BookException - AlreadyExistBookException
            throw new RuntimeException("이미 존재합니다.");
        });
        bookDao.registerBook(bookDto);
    }

    // to do -> 카테고리 이름 내역으로 조회가 가능해야한다.
    public void findBookByTitle(String title) {
        Optional<Book> bookByTitle = bookDao.findBookByTitle(title);
        bookByTitle.ifPresent((book) -> {
            throw new RuntimeException("이미 존재하는 책입니다.");
        });
    }

    public void deleteBookById(Long bookId) {
        if (!UserHolder.isRoot()) {
            throw new RuntimeException("삭제할 권한이 존재하지 않습니다.");
        }
        bookDao.deleteBook(bookId);
    }

    public void updateBook(Long bookId, BookDto bookDto) {
        bookDao.updateBook(bookId, bookDto);
    }


    public List<Book> findAllBookWithCount() {
        return bookDao.findAllBookWithCount();
    }

    public List<Book> findBooksContainsTitle(String title) {
        return bookDao.findBookContainsTitle(title);
    }

    public List<Book> findBookByCategoryName(String categoryName) {
        return bookDao.findBookByCategoryName(categoryName);
    }

    public void registerBookWithCategories(BookRegisterDto dto, List<String> inputCategories) {
        bookDao.registerBookWithCategories(dto, inputCategories);
    }

    public void updateBookWithCategories(BookRegisterDto dto, Long bookId, List<String> updatedCategories) {
        System.out.println(bookId);
        List<String> currentCategories = categoriesDao.findAllCategory(bookId);

        bookDao.updateBookWithCategories(dto, bookId, currentCategories, updatedCategories);
    }
}
