package com.booklink.ui.panel.content.booksummary;

import com.booklink.model.book.Book;
import com.booklink.ui.panel.content.ContentPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BookSummaryPanel extends JPanel {
    private static int width = 1488;
    private static int height = 200;

    public BookSummaryPanel(Book book, ContentPanel parentPanel) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(width, height)); // 너비를 1488로 수정
        setBorder(BorderFactory.createLineBorder(Color.YELLOW));

        JLabel imageLabel = new JLabel(new ImageIcon());
        imageLabel.setPreferredSize(new Dimension(200, height)); // 이미지 크기 설정
        imageLabel.setBackground(Color.BLACK);
        imageLabel.setOpaque(true);
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
        imageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                parentPanel.showBookDetail(book);
            }
        });

        add(imageLabel, BorderLayout.WEST);

        BookSummaryContentPanel contentPanel = new BookSummaryContentPanel(width - 200, height, book);
        add(contentPanel, BorderLayout.CENTER);
    }
}
