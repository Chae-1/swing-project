package com.booklink.ui.panel.content;

import com.booklink.controller.BookController;

import com.booklink.model.book.Book;
import com.booklink.model.book.BookListWithCount;
import com.booklink.ui.panel.content.bookdetail.BookDetailPanel;
import com.booklink.ui.panel.content.booksummary.BookSummaryPanel;
import java.awt.*;
import java.util.List;
import javax.swing.*;
// 1488, 970


public class ContentPanel extends JPanel {

    private final int pageSize = 5;
    private int currentPage = 1;
    private int totalPageCount;
    private JPanel bookContainer;
    private JPanel pagingPanel;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private BookController bookController;
    private BookListWithCount booksWithCount;
    private int width;
    private int height;

    public ContentPanel(int width, int height) {
        this.width = width;
        this.height = height;
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        setLayout(new BorderLayout());
        setSize(width, height);
        setBackground(Color.LIGHT_GRAY);
        bookController = new BookController();
        booksWithCount = bookController.findAllBookWithCount();

        bookContainer = new JPanel();
        bookContainer.setLayout(new BoxLayout(bookContainer, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(bookContainer);

        pagingPanel = new JPanel();

        JPanel bookListPanel = new JPanel(new BorderLayout());
        bookListPanel.add(scrollPane, BorderLayout.CENTER);
        bookListPanel.add(pagingPanel, BorderLayout.SOUTH);

        mainPanel.add(bookListPanel, "BookList");
        add(mainPanel, BorderLayout.CENTER);
        loadBooks();
        updateBookSummaryPanels(width, height);
        updatePagingPanel();
    }

    private void loadBooks() {
        totalPageCount = (int) Math.ceil((double) booksWithCount.count() / pageSize);
    }

    private void updateBookSummaryPanels(int width, int height) {
        bookContainer.removeAll();
        List<Book> books = booksWithCount.books();
        int start = (currentPage - 1) * pageSize;
        int end = Math.min(start + pageSize, books.size());
        for (int i = start; i < end; i++) {
            BookSummaryPanel bookSummaryPanel = new BookSummaryPanel(width, (height - 300) / 5, books.get(i), this);
            bookContainer.add(bookSummaryPanel);
        }
        bookContainer.revalidate();
        bookContainer.repaint();
    }

    private void updatePagingPanel() {
        pagingPanel.removeAll();
        for (int i = 1; i <= totalPageCount; i++) {
            JButton pageButton = new JButton(String.valueOf(i));
            pageButton.addActionListener(e -> {
                currentPage = Integer.parseInt(pageButton.getText());
                updateBookSummaryPanels(width, height);
            });
            pagingPanel.add(pageButton);
        }
        pagingPanel.revalidate();
        pagingPanel.repaint();
    }

    public void showBookDetail(Book book) {
        BookDetailPanel bookDetailPanel = new BookDetailPanel(book, this);
        mainPanel.add(bookDetailPanel, "BookDetail");
        cardLayout.show(mainPanel, "BookDetail");
    }

    public void showBookList() {
        cardLayout.show(mainPanel, "BookList");
    }

}