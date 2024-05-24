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

        setLayout(null); // 레이아웃을 null로 설정하여 절대 위치 사용
        setSize(width, height);
        setBackground(Color.LIGHT_GRAY);
        bookController = new BookController();
        booksWithCount = bookController.findAllBookWithCount();


        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBounds(0, 0, width, height - 50); // 위치와 크기 설정
        mainPanel.setLayout(null);

        bookContainer = new JPanel();
        bookContainer.setLayout(new BoxLayout(bookContainer, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(bookContainer);
        scrollPane.setBounds(0, 0, width, height - 100); //

        pagingPanel = new JPanel(new FlowLayout());
        pagingPanel.setBounds(0, height - 100, width, 50); // 위치와 크기 설정

        JPanel bookListPanel = new JPanel(null);
        bookListPanel.add(scrollPane);
        bookListPanel.add(pagingPanel);

        mainPanel.add(bookListPanel, "BookList");
        add(mainPanel);

        loadBooks();
        updateBookSummaryPanels();
        updatePagingPanel();
    }

    private void loadBooks() {
        totalPageCount = (int) Math.ceil((double) booksWithCount.count() / pageSize);
    }

    private void updateBookSummaryPanels() {
        bookContainer.removeAll();
        List<Book> books = booksWithCount.books();
        int start = (currentPage - 1) * pageSize;
        int end = Math.min(start + pageSize, books.size());
        for (int i = start; i < end; i++) {
            BookSummaryPanel bookSummaryPanel = new BookSummaryPanel(books.get(i), this);
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
                updateBookSummaryPanels();
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