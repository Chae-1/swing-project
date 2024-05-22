package com.booklink.service;

import com.booklink.dao.BookDao;
import com.booklink.model.book.Book;
import com.booklink.model.book.BookDto;

import java.util.Optional;

public class BookService {
    private final BookDao bookDao;

    public BookService(BookDao bookDao) {
        this.bookDao = bookDao;
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


    public void findBookByTitle(String title) {
        Optional<Book> bookByTitle = bookDao.findBookByTitle(title);
        bookByTitle.ifPresent((book) -> {
            System.out.println(book.bookShortInfo().getSummary());
        });
    }
}
