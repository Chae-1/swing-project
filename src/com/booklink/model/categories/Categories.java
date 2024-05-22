package com.booklink.model.categories;

public class Categories {
    private Long id;
    private String name;
    private Categories categories;

    public Categories(Long id, String name, Categories categories) {
        this.id = id;
        this.name = name;
        this.categories = categories;
    }
}
