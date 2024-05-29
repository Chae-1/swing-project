package com.booklink.ui.panel.content;

import com.booklink.ui.panel.content.book.BookContentPanel;

import javax.swing.*;
import java.awt.*;

public class PagingPanel extends JPanel {

    private static final int SIZE = 10;
    private ContentPanel contentPanel;
    private int width;
    private int height;

    public PagingPanel(int width, int height, ContentPanel contentPanel) {
        setPreferredSize(new Dimension(width, height));
        setLayout(new FlowLayout());
        setBackground(Color.GRAY);
        this.width = width;
        this.height = height;
        // pagingPanel의 부모가 되는 패널
        this.contentPanel = contentPanel;
        updatePagingPanel();
    }

    // 페이지를 아예 지워버리고
    // 현재 페이지 번호에서 10개 혹은 마지막 페이지까지 생성한다.
    public void updatePagingPanel() {
        removeAll();
        setPreferredSize(new Dimension(width, height));
        setLayout(new FlowLayout());
        setBackground(Color.GRAY);

        int startNum = Math.max(1, contentPanel.getCurrentPage() - 5);
        int endNum = Math.min(startNum + 10, contentPanel.getMaxPage());
        System.out.println(startNum);
        System.out.println(endNum);
        for (int i = startNum; i <= endNum; i++) {
            JButton pageButton = new JButton(String.valueOf(i));
            pageButton.addActionListener(e -> {
                contentPanel.update(Integer.parseInt(pageButton.getText()));
            });
            pageButton.setPreferredSize(new Dimension(50, 30));
            add(pageButton);
        }
        revalidate();
        repaint();
    }
}
