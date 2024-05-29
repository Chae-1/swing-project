package com.booklink.model.book;

import java.time.LocalDate;

public record BookDto(String title,
                      String author,
                      LocalDate publicationDate,
                      String summary,
                      String description,
                      Integer price,
                      String publisher,
                      Integer salesPoint,
                      Double rating,
                      String imageUrl) {
}
