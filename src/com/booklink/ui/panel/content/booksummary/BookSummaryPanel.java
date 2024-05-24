package com.booklink.ui.panel.content.booksummary;

import com.booklink.model.book.Book;
import com.booklink.ui.panel.content.ContentPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class BookSummaryPanel extends JPanel {
    private static int SUMMARY_PANEL_WIDTH = 1488;
    private static int SUMMARY_PANEL_HEIGHT = 150;

    public BookSummaryPanel(int width, int height) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(width, height)); // 너비를 1488로 수정
        setBorder(BorderFactory.createLineBorder(Color.YELLOW));

        JLabel imageLabel = new JLabel(new ImageIcon());
        imageLabel.setPreferredSize(new Dimension(200, height)); // 이미지 크기 설정
        imageLabel.setBackground(Color.BLACK);
        imageLabel.setOpaque(true);
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.YELLOW));

        add(imageLabel, BorderLayout.WEST);

        BookSummaryContentPanel contentPanel = new BookSummaryContentPanel(width - 200, height);
        add(contentPanel, BorderLayout.CENTER);
    }

    static class TestFrame extends JFrame {
        public TestFrame() {
            setLayout(null);
            setSize(1920, 1080); // 프레임 크기 설정
            BookSummaryPanel comp = new BookSummaryPanel(SUMMARY_PANEL_WIDTH, SUMMARY_PANEL_HEIGHT);
            add(comp).setBounds(0, 0, SUMMARY_PANEL_WIDTH, SUMMARY_PANEL_HEIGHT); // BookSummaryContentPanel 추가
        }

        public static void main(String[] args) {
            TestFrame testFrame = new TestFrame();
            SwingUtilities.invokeLater(() -> {
                testFrame.setVisible(true);
            });
        }
    }
}