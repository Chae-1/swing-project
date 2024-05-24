package com.booklink.model.book;

import java.util.List;

public record BookListWithCount(List<Book> books,
                                Integer count) {
}
