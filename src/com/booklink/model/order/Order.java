package com.booklink.model.order;

import java.time.LocalDateTime;

public class Order {
    private Long orderId;
    private Integer amount;
    private LocalDateTime orderDate;

    public Order(Long orderId, Integer amount, LocalDateTime orderDate) {
        this.orderId = orderId;
        this.amount = amount;
        this.orderDate = orderDate;
    }
}
