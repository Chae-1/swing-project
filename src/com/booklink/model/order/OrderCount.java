package com.booklink.model.order;

public class OrderCount {
    private final Integer orderCount;

    public OrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }


    public boolean isPurchased() {
        return orderCount != null && orderCount > 0;
    }
}
