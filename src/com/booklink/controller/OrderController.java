package com.booklink.controller;

import com.booklink.model.book.exception.BookNotExistException;
import com.booklink.model.order.OrderDto;
import com.booklink.service.OrderService;

import java.util.List;

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

    public List<OrderDto> findAllOrderByUserId(Long userId) {
        return orderService.findAllOrderByUserId(userId);
    }
}
