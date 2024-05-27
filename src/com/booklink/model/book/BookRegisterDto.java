package com.booklink.model.book;

import java.time.LocalDate;

public record BookRegisterDto(String title,
                              String author,
                              LocalDate publicationDate,
                              String summary,
                              String description,
                              Integer price,
                              String publisher,
                              String category1,
                              String category2,
                              String imageUrl) {
}
