package com.booklink.model.book.comments;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record CommentDto(Long commentId,
                         String name,
                         Integer rating,
                         LocalDateTime regDate,
                         String comment) {

}
