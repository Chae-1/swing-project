package com.booklink.ui.panel.content;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class BookSummaryContentPanel extends JPanel {
    public BookSummaryContentPanel() {
        setPreferredSize(new Dimension(608, 230));
        setBorder(BorderFactory.createLineBorder(Color.RED)); // 테두리 색상은 가시성을 위해 설정했습니다.
        setBackground(Color.WHITE); // 배경색 설정

        JLabel titleLabel = new JLabel("Book Title");
        titleLabel.setBounds(0, 0, 608, 40);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // 가시성을 위해 테두리 추가

        // AuthorLabel 설정
        JLabel authorLabel = new JLabel("Author | Publisher");
        authorLabel.setBounds(0, 50, 608, 30);
        authorLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        authorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        authorLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // 가시성을 위해 테두리 추가

        // SummaryLabel 설정
        JTextArea summaryLabel = new JTextArea("This is a summary of the book. It provides an overview of the book's content.");
        summaryLabel.setBounds(0, 90, 608, 130);
        summaryLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        summaryLabel.setLineWrap(true);
        summaryLabel.setWrapStyleWord(true);
        summaryLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // 가시성을 위해 테두리 추가
        summaryLabel.setEditable(false); // 수정 불가 설정
        summaryLabel.setOpaque(false); // 배경색 설정을 위해 투명화 설정

        // 패널에 레이블 추가
        add(titleLabel);
        add(authorLabel);
        add(summaryLabel);
    }
}