package com.booklink.ui.panel.content;

import com.booklink.ui.panel.content.booksummary.BookSummaryPanel;

import java.awt.*;
import javax.swing.*;
// 1488, 970


public class ContentPanel extends JPanel {

    public ContentPanel(int width, int height) {
        setLayout(new FlowLayout());
        setSize(width, height);
        setBackground(Color.LIGHT_GRAY);

        for (int i = 0; i < 5; i++) {
            BookSummaryPanel bookSummaryPanel = new BookSummaryPanel(width, (height - 300) / 5);
            add(bookSummaryPanel);
        }

        JPanel pagingPanel = new PagingPanel(width, height);
        add(pagingPanel);
    }

    static class TestFrame extends JFrame {
        public TestFrame() {
            setLayout(new BorderLayout());
            setSize(1980, 1020);
            add(new ContentPanel(WIDTH, HEIGHT), BorderLayout.CENTER);
        }

        public static void main(String[] args) {
            TestFrame testFrame = new TestFrame();
            SwingUtilities.invokeLater(() -> {
                    testFrame.setVisible(true);
            });
        }
    }
}