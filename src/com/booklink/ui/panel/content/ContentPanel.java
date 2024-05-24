package com.booklink.ui.panel.content;

import com.booklink.controller.BookController;

import com.booklink.model.book.Book;
import com.booklink.model.book.BookListWithCount;
import com.booklink.ui.panel.content.booksummary.BookSummaryPanel;
import java.awt.*;
import java.util.List;
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
//    private void loadBooks() {
//        totalPageCount = (int) Math.ceil((double) booksWithCount.count() / pageSize);
//    }
//
//    private void updateBookSummaryPanels() {
//        bookContainer.removeAll();
//        List<Book> books = booksWithCount.books();
//        int start = (currentPage - 1) * pageSize;
//        int end = Math.min(start + pageSize, books.size());
//        for (int i = start; i < end; i++) {
//            BookSummaryPanel bookSummaryPanel = new BookSummaryPanel(books.get(i), this);
//            bookContainer.add(bookSummaryPanel);
//        }
//        bookContainer.revalidate();
//        bookContainer.repaint();
//    }

//    private void updatePagingPanel() {
//        pagingPanel.removeAll();
//        for (int i = 1; i <= totalPageCount; i++) {
//            JButton pageButton = new JButton(String.valueOf(i));
//            pageButton.addActionListener(e -> {
//                currentPage = Integer.parseInt(pageButton.getText());
//                updateBookSummaryPanels();
//            });
//            pagingPanel.add(pageButton);
//        }
//        pagingPanel.revalidate();
//        pagingPanel.repaint();
//    }
//
//    public void showBookDetail(Book book) {
//        BookDetailPanel bookDetailPanel = new BookDetailPanel(book, this);
//        mainPanel.add(bookDetailPanel, "BookDetail");
//        cardLayout.show(mainPanel, "BookDetail");
//    }
//
//    public void showBookList() {
//        cardLayout.show(mainPanel, "BookList");
//    }