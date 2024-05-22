package com.booklink.ui.panel.content;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

public class CategoryPanel extends JPanel {
    public CategoryPanel() {
        setPreferredSize(new Dimension(432, 970));
        setLayout(new BorderLayout());

        // 트리 루트 노드 생성
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("전체");

        // 소설 카테고리와 하위 카테고리 추가
        DefaultMutableTreeNode novelNode = new DefaultMutableTreeNode("소설");
        novelNode.add(new DefaultMutableTreeNode("판타지 소설"));
        novelNode.add(new DefaultMutableTreeNode("SF 소설"));
        root.add(novelNode);

        // 비문학 카테고리와 하위 카테고리 추가
        DefaultMutableTreeNode nonFictionNode = new DefaultMutableTreeNode("비문학");
        nonFictionNode.add(new DefaultMutableTreeNode("역사"));
        nonFictionNode.add(new DefaultMutableTreeNode("과학"));
        root.add(nonFictionNode);

        // 트리 모델 생성
        JTree categoryTree = new JTree(root);

        // 스크롤 가능한 패널에 트리 추가
        JScrollPane scrollPane = new JScrollPane(categoryTree);

        add(scrollPane, BorderLayout.CENTER);
    }
}