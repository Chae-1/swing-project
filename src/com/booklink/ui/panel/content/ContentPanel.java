package com.booklink.ui.panel.content;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ContentPanel extends JPanel {

    public ContentPanel() {
        // 패널 크기 설정
        setPreferredSize(new Dimension(1008, 900));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        // 임의의 배경 색상 설정 (보기 쉽게 하기 위해)
        setBackground(Color.LIGHT_GRAY);

        // 빈 BookSummaryPanel 4개 추가
        for (int i = 0; i < 4; i++) {
            BookSummaryPanel bookSummaryPanel = new BookSummaryPanel();
            bookSummaryPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // 패널을 수직 중앙에 정렬
            add(bookSummaryPanel);
            add(Box.createVerticalStrut(5)); // 각 패널 사이에 공간 추가
        }

        JPanel pagingPanel = new JPanel();
        pagingPanel.setPreferredSize(new Dimension(1008, 50));
        pagingPanel.setBackground(Color.GRAY);

        // 페이징 버튼 생성 및 추가
        for (int i = 1; i <= 10; i++) {
            JButton pageButton = new JButton(String.valueOf(i));
            pageButton.setPreferredSize(new Dimension(50, 30));
            pagingPanel.add(pageButton);
        }
        pagingPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // 패널을 수직 중앙에 정렬
        add(pagingPanel);
    }
}