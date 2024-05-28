package com.booklink.controller;

import com.booklink.model.book.Book;
import com.booklink.model.book.exception.BookNotExistException;
import com.booklink.service.BookService;
import com.booklink.service.OrderService;
import com.booklink.service.UserService;

import java.util.Optional;

public class OrderController {
    private final OrderService orderService;

    public OrderController() {
        this.orderService = new OrderService();
    }

    /**
     *
     * @throws com.booklink.model.user.exception.UserNotFoundException
     * @throws BookNotExistException
     */
    public void createOrder(Long bookId, Long userId) {
        // 유저 정보는 존재한다고 가정한다.

        orderService.createOrder(bookId, userId);
    }
}
