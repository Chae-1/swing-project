package com.booklink.model.book;

public record BookDto(String title,
                      String author,
                      String summary,
                      String description,
                      Integer price,
                      String publisher,
                      Integer salesPoint,
                      Double rating) {
}
