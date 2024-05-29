package com.booklink.service;

import com.booklink.dao.CategoriesDao;
import com.booklink.model.categories.Categories;
import com.booklink.model.categories.CategoryDto;
import com.booklink.model.categories.CategoryWithLevelDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CategoryService {
    private final CategoriesDao dao = new CategoriesDao();

    public List<Categories> allRootAndChildCategories() {
        List<CategoryWithLevelDto> dtos = dao.allCategoriesWithLevel();
        Map<Long, Categories> roots = mappingRootAndChildCategory(dtos);
        return new ArrayList<>(roots.values());
    }

    private static Map<Long, Categories> mappingRootAndChildCategory(List<CategoryWithLevelDto> dtos) {
        Map<Long, Categories> roots = new HashMap<>();
        for (CategoryWithLevelDto dto : dtos) {
            // 루트 카테고리이면, 이전 priorId가 0이고
            if (dto.priorId() == 0) {
                roots.put(dto.id(), new Categories(dto.id(), dto.name()));
            } else {
                // 루트 카테고리가 존재하면
                // 서브 카테고리를 루트 카테고리의 하위로 등록
                for (Categories root : roots.values()) {
                    root.addSubCategory(dto.priorId(), dto.name(), dto.id());
                }
            }
        }
        return roots;
    }

    public List<String> findAllCategoriesByBookId(Long bookId) {
        return dao.findAllCategoryPath(bookId);
    }

    public List<String> findAllCategoryNames() {
        List<CategoryWithLevelDto> dtos = dao.allCategoriesWithLevel();
        return dtos.stream()
                .map(dto -> dto.name())
                .collect(Collectors.toList());


    }

    public List<CategoryDto> findAllCategories(){
        return dao.allCategories();
    }

    public List<String> findAll() {
        return dao.findAll();
    }
}