package com.booklink.ui.panel.content.book.booksummary;

import com.booklink.model.book.Book;
import com.booklink.ui.frame.main.MainFrame;
import com.booklink.ui.panel.content.ContentPanel;
import com.booklink.ui.panel.content.book.bookdetail.BookDetailPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.*;

public class BookSummaryPanel extends JPanel {
    private static int SUMMARY_PANEL_WIDTH = 1488;
    private static int SUMMARY_PANEL_HEIGHT = 150;
    private ContentPanel contentPanel;

    public BookSummaryPanel(int width, int height, ContentPanel contentPanel, Book book) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(width, height)); // 너비를 1488로 수정
        setBorder(BorderFactory.createLineBorder(Color.YELLOW));
        this.contentPanel = contentPanel;


        JLabel imageLabel = null;
        try {
            String imageUrl = book.getImageUrl();
            System.out.println(imageUrl);
            imageLabel = new JLabel(new ImageIcon(new URL(imageUrl)));
        } catch (MalformedURLException e) {
            e.printStackTrace();
            imageLabel = new JLabel(new ImageIcon());
        }
        // 안좋은 방법이지만,
        // 해당 이미지 라벨을 클릭하면 book을 넘기고
        // 도서에 대한 디테일한 설명과 댓글 목록을 가지고 오게 할거임
        imageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("이벤트 발생");
                MainFrame mainFrame = (MainFrame) SwingUtilities.getWindowAncestor(BookSummaryPanel.this);
                mainFrame.changeCurrentContent(new BookDetailPanel(mainFrame, book));
            }
        });
        imageLabel.setPreferredSize(new Dimension(200, height)); // 이미지 크기 설정
        imageLabel.setBackground(Color.BLACK);
        imageLabel.setOpaque(true);
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.YELLOW));

        add(imageLabel, BorderLayout.WEST);

        BookSummaryContentPanel summaryContentPanel = new BookSummaryContentPanel(width - 200, height, book);
        add(summaryContentPanel, BorderLayout.CENTER);
    }
}