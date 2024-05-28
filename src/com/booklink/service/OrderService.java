package com.booklink.service;

import com.booklink.dao.BookDao;
import com.booklink.dao.OrderDao;
import com.booklink.dao.UserDao;

public class OrderService {

    private final UserDao userDao;
    private final OrderDao orderDao;
    private final BookDao bookDao;

    public OrderService() {
        this.userDao = new UserDao();
        this.orderDao = new OrderDao();
        this.bookDao = new BookDao();
    }

    public void createOrder(Long bookId, Long userId) {

    }

    public static void main(String[] args) {
        System.out.println("123");
    }
}
