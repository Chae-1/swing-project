package com.booklink.ui.panel.content;

import com.booklink.controller.CategoryController;
import com.booklink.model.categories.Categories;
import com.booklink.model.categories.CategoryDto;
import com.booklink.ui.frame.main.MainFrame;
import com.booklink.ui.panel.content.book.BookContentPanel;
import com.booklink.ui.panel.content.book.CategoryNode;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

public class CategoryPanel extends JPanel {
    private final CategoryController controller = new CategoryController();

    public CategoryPanel(int width, int height, MainFrame mainFrame) {
        setPreferredSize(new Dimension(width, height));
        setLayout(new BorderLayout());

        // 트리 루트 노드 생성
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("전체");

        List<Categories> rootCategories = controller.requestAllRootAndChildCategories();
        for (Categories rootCategory : rootCategories) {
            CategoryNode treeNode = rootCategory.toTreeNode();
            root.add(treeNode);
        }

        // 트리 모델 생성
        JTree categoryTree = new JTree(root);
        categoryTree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = categoryTree.getRowForLocation(e.getX(), e.getY());
                if (selectedRow != -1) {
                    DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) categoryTree.getPathForRow(selectedRow).getLastPathComponent();
                    String categoryName = selectedNode.toString();
                    mainFrame.changeCurrentContent(new BookContentPanel(mainFrame, new CategoryDto(categoryName, null)));
                }
            }
        });
        // 스크롤 가능한 패널에 트리 추가
        JScrollPane scrollPane = new JScrollPane(categoryTree);
        add(scrollPane, BorderLayout.CENTER);
    }
}