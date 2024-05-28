package com.booklink.model.order;

import java.time.LocalDateTime;

public record OrderDto(Long orderId,
                       Long bookId,
                       String bookTitle,
                       LocalDateTime purchasedDate,
                       Integer price,
                       String bookImageUrl) {
}
