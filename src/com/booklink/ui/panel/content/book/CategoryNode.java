package com.booklink.ui.panel.content.book;

import javax.swing.tree.DefaultMutableTreeNode;

public class CategoryNode extends DefaultMutableTreeNode {
    private final Long id;
    private final String name;

    public CategoryNode(Long id, String name) {
        super(name); // 노드의 디스플레이 이름 설정
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name; // JTree 노드의 이름으로 표시될 값 반환
    }
}
