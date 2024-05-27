package com.booklink.model.categories;

import com.booklink.ui.panel.content.book.CategoryNode;

import java.util.ArrayList;
import java.util.List;

public class Categories {
    private Long id;
    private String name;
    private List<Categories> subCategories;

    public Categories(Long id, String name) {
        this.id = id;
        this.name = name;
        subCategories = new ArrayList<>();
    }

    public void addSubCategory(Long priorId, String name, Long id) {
        if (priorId == this.id) {
            this.subCategories.add(new Categories(id, name));
            return;
        }

        // 서브 카테고리가 없으면 탐색 중단
        for (Categories subCategory : subCategories) {
            subCategory.addSubCategory(priorId, name, id);
        }
    }

    public CategoryNode toTreeNode() {
        CategoryNode treeNode = new CategoryNode(id, name);
        for (Categories subCategory : subCategories) {
            treeNode.add(subCategory.toTreeNode());
        }
        return treeNode;
    }

    public String getName() {
        return name;
    }
}
