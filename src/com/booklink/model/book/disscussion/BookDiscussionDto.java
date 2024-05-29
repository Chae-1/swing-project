package com.booklink.model.book.disscussion;

import java.time.LocalDateTime;

public record BookDiscussionDto(Long discussionId,
                                LocalDateTime discussionDate,
                                String content,
                                String title,
                                Long bookId,
                                Long userId,
                                String userName)    {
}
