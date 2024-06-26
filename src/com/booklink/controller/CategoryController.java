package com.booklink.controller;

import com.booklink.model.categories.Categories;
import com.booklink.service.CategoryService;

import java.util.List;

public class CategoryController {
    private final CategoryService categoryService = new CategoryService();
    public List<Categories> requestAllRootAndChildCategories() {
        return categoryService.allRootAndChildCategories();
    }

    public List<String> findAllCategories(Long bookId) {
        return categoryService.findAllCategoriesByBookId(bookId);
    }

    public List<String> findAllCategoryNames() {
        return categoryService.findAllCategoryNames();
    }


    public List<String> findAll() {
        return categoryService.findAll();
    }
}