package com.booklink.ui.panel.content;

import com.booklink.model.book.Book;
import com.booklink.ui.frame.main.MainFrame;
import com.booklink.ui.panel.content.book.bookdetail.BookDetailPanel;

import java.awt.*;
import javax.swing.*;
// 1488, 970


public class ContentPanel extends JPanel {
    protected int contentWidth = MainFrame.WIDTH - (MainFrame.WIDTH / 4) - 20 - 250;
    protected int contentHeight = MainFrame.HEIGHT - 50 - 100;

    private MainFrame mainFrame;

    public ContentPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        init();
    }

    private void init() {
        setLayout(new FlowLayout());
        setSize(contentWidth, contentHeight);
        setBackground(Color.LIGHT_GRAY);
    }

    // 상위
    public void moveOtherPanel(Book contentBook) {
        mainFrame.loadPrevContent(this);
        // BookDetailPanel을 생성해서 mainFrame으로 전달하면 된다.
        mainFrame.changeCurrentContent(new BookDetailPanel(mainFrame, contentBook));
    }

    static class TestFrame extends JFrame {
        public TestFrame() {
            setLayout(new BorderLayout());
            setSize(1980, 1020);
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