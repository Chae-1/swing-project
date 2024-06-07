package com.booklink.model.order;

import com.booklink.model.book.Book;
import com.booklink.model.user.User;

import java.time.LocalDateTime;

public class Order {
    private Long orderId;
    private Integer amount;
    private LocalDateTime orderDate;
    private User user;
    private Book book;

    public Order(Long orderId, Integer amount, LocalDateTime orderDate) {
        this.orderId = orderId;
        this.amount = amount;
        this.orderDate = orderDate;
    }
}
