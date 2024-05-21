package com.booklink.model.book;

import com.booklink.model.categories.Categories;

public class Book {
    private Long id;
    private String title;
    private String author;
    private String summary;
    private String description;
    private Integer price;
    private String publisher;
    private Integer salesPoint;
    private Double rating;
    private Categories categories;


    public Book(String title, String author, String summary,
                String description, Integer price, String publisher,
                Integer salesPoint, Categories categories) {
        this.title = title;
        this.author = author;
        this.summary = summary;
        this.description = description;
        this.price = price;
        this.publisher = publisher;
        this.salesPoint = salesPoint;
    }
}
