package com.booklink.ui.panel.content;

import com.booklink.controller.CategoryController;
import com.booklink.model.categories.Categories;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

public class CategoryPanel extends JPanel {
    private final CategoryController controller = new CategoryController();

    public CategoryPanel(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setLayout(new BorderLayout());

        // 트리 루트 노드 생성
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("전체");

        // 루트 카테고리만 가지고 온다.
        // 그 밑에 서브 카테고리가 매핑되어있다.
        List<Categories> rootCategories = controller.requestAllRootAndChildCategories();

        // 소설 카테고리와 하위 카테고리 추가

        for (Categories rootCategory : rootCategories) {
            root.add(rootCategory.toTreeNode());
        }
        // 트리 모델 생성
        JTree categoryTree = new JTree(root);

        // 스크롤 가능한 패널에 트리 추가
        JScrollPane scrollPane = new JScrollPane(categoryTree);

        add(scrollPane, BorderLayout.CENTER);
    }
}