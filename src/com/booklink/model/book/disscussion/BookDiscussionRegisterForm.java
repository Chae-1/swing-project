package com.booklink.model.book.disscussion;

public record BookDiscussionRegisterForm(Long bookId,
                                         Long userId,
                                         String title,
                                         String content) {
}
