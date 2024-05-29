package com.booklink.model.book.disscussion.comment;

import java.time.LocalDateTime;

public record DiscussionCommentDto(
        Long commentId,
        String userName,
        String comment,
        LocalDateTime regDate,
        Long userId) {
}
