package com.booklink.ui.panel.content.book.booksummary;

import com.booklink.model.book.Book;

import javax.swing.*;
import java.awt.*;

public class BookSummaryContentPanel extends JPanel {
    public BookSummaryContentPanel(int width, int height, Book book) {
        setLayout(null); // 레이아웃 매니저를 null로 설정하여 절대 위치 사용
        setPreferredSize(new Dimension(width, height)); // 패널 크기 설정
        setBorder(BorderFactory.createLineBorder(Color.RED));
        setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel(book.getTitle());
        titleLabel.setBounds(0, 0, width, 30); // titleLabel 크기 설정
        titleLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel authorLabel = new JLabel(book.getAuthor() + " | " + book.getPublisher());
        authorLabel.setBounds(0, 50, width, 30); // authorLabel 크기 설정
        authorLabel.setFont(new Font("Malgun Gothic", Font.PLAIN, 14));
        authorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        authorLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JTextArea summaryLabel = new JTextArea(book.getSummary());
        summaryLabel.setBounds(0, 90, width, height - 90); // summaryLabel 크기 설정
        summaryLabel.setFont(new Font("Malgun Gothic", Font.PLAIN, 12));
        summaryLabel.setLineWrap(true);
        summaryLabel.setWrapStyleWord(true);
        summaryLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        summaryLabel.setEditable(false);
        summaryLabel.setOpaque(false);

        add(titleLabel);
        add(authorLabel);
        add(summaryLabel);
    }

    // TestFrame 클래스
    static class TestFrame extends JFrame {
        public TestFrame() {
            setLayout(new BorderLayout());
            setSize(1440, 1020); // 프레임 크기 설정
//            add(new BookSummaryContentPanel(1288, 150, book), BorderLayout.CENTER); // BookSummaryContentPanel 추가
        }

        public static void main(String[] args) {
            TestFrame testFrame = new TestFrame();
            SwingUtilities.invokeLater(() -> {
                testFrame.setVisible(true);
            });
        }
    }
}