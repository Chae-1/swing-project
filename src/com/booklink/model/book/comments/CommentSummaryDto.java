package com.booklink.model.book.comments;

import java.time.LocalDateTime;

public record CommentSummaryDto(Long commentId,
                                String userName,
                                int rating,
                                LocalDateTime regDate,
                                String comment) {
}
