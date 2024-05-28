package com.booklink.model.book.disscussion;

import java.time.LocalDateTime;

public class BookDiscussion {
    private Long discussionId;
    private LocalDateTime discussionDate;
    private String content;
    private String title;
    private Long userId;
    private Long bookId;

    public BookDiscussion(Long discussionId, LocalDateTime discussionDate, String content, String title, Long userId, Long bookId) {
        this.discussionId = discussionId;
        this.discussionDate = discussionDate;
        this.content = content;
        this.title = title;
        this.userId = userId;
        this.bookId = bookId;
    }
}
