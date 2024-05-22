package com.booklink.ui.panel.content;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class BookSummaryPanel extends JPanel {
    public BookSummaryPanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1008, 190));
        setBorder(BorderFactory.createLineBorder(Color.YELLOW));
        // 이미지 레이블을 생성하고 크기와 위치를 설정합니다.
        JLabel imageLabel = new JLabel(new ImageIcon());
        imageLabel.setPreferredSize(new Dimension(400, 190)); // 이미지 레이블 크기 설정
        imageLabel.setBackground(Color.BLACK);
        imageLabel.setOpaque(true); // 배경 색상이 보이도록 설정
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
        // 이미지 레이블을 좌측에 배치합니다.
        add(imageLabel, BorderLayout.WEST);

        BookSummaryContentPanel contentPanel = new BookSummaryContentPanel();
        add(contentPanel, BorderLayout.CENTER);
    }
}