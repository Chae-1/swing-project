package com.booklink.model.book.comments;

import java.time.LocalDateTime;

public record  CommentFormDto(Long userId,
                             Long bookId,
                             Integer rating,
                             LocalDateTime regDate,
                             String content) {
}
