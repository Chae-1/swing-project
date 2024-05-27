package com.booklink.model.book.comments;

import com.booklink.model.book.Book;
import com.booklink.model.user.User;
import java.time.LocalDateTime;

/**
 *     user_id int,
 *     book_id INTEGER,
 *     comment_rating INTEGER,
 *     comment_reg_date timestamp,
 *     comment_content CLOB,
 *     comment_is_purchased VARCHAR2(20)
 */
public class Comments {
    private String comment;
    private LocalDateTime writeDateTime;
    private Integer rating;
    private String isPurchased;
    private Long bookId;
    private Long userId;

    public Comments(String comment, LocalDateTime writeDateTime, Integer rating, String isPurchased, Long bookId,
                    Long userId) {
        this.comment = comment;
        this.writeDateTime = writeDateTime;
        this.rating = rating;
        this.isPurchased = isPurchased;
        this.bookId = bookId;
        this.userId = userId;
    }
}
