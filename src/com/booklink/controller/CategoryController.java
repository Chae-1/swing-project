package com.booklink.controller;

import com.booklink.model.categories.Categories;
import com.booklink.service.CategoryService;

import java.util.List;

public class CategoryController {
    private final CategoryService categoryService = new CategoryService();
    public List<Categories> requestAllRootAndChildCategories() {
        return categoryService.allRootAndChildCategories();
    }
}