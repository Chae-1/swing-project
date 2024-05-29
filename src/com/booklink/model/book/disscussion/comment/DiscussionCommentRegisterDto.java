package com.booklink.model.book.disscussion.comment;
// UserHolder.getId(), discussionId,
// LocalDateTime.now(), text

import java.time.LocalDateTime;

public record DiscussionCommentRegisterDto(Long userId,
                                           Long discussionId,
                                           LocalDateTime now,
                                           String content) {

}
