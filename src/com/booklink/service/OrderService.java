package com.booklink.service;

import com.booklink.dao.BookDao;
import com.booklink.dao.OrderDao;
import com.booklink.dao.UserDao;
import com.booklink.model.book.Book;
import com.booklink.model.book.exception.BookNotExistException;
import com.booklink.model.user.User;
import com.booklink.model.user.exception.UserNotFoundException;

public class OrderService {

    private final UserDao userDao;
    private final OrderDao orderDao;
    private final BookDao bookDao;

    public OrderService() {
        this.userDao = new UserDao();
        this.orderDao = new OrderDao();
        this.bookDao = new BookDao();
    }
    // 1. 유효성 검증, bookId, userid 대한 정보를 조회하고 이들이 없다면 예외가 발생
    // 2. 유효하다면, order 생성 -> 트랜잭션 사용, 지금 없음.
    // OrderItem

    /**
     *
     * @throws UserNotFoundException
     * @throws BookNotExistException
     */
    public void createOrder(Long bookId, Long userId) {
        // 1. 유효성 검증
        User user = userDao.findById(userId)
                .orElseThrow(() -> new UserNotFoundException());
        Book book = bookDao.findBookById(bookId)
                .orElseThrow(() -> new BookNotExistException());

        // 2. order 생성
        orderDao.createOrder(bookId, userId, book.getPrice());
    }
}
