package com.booklink.ui.panel.content.book;

import com.booklink.controller.BookController;
import com.booklink.model.book.Book;
import com.booklink.model.categories.CategoryDto;
import com.booklink.ui.frame.main.MainFrame;
import com.booklink.ui.panel.content.ContentPanel;
import com.booklink.ui.panel.content.PagingPanel;
import com.booklink.ui.panel.content.book.booksummary.BookSummaryPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;
// 1488, 970


public class BookContentPanel extends ContentPanel {
    private BookController bookController;
    private PagingPanel pagingPanel;

    private List<Book> books;
    private int currentPage;
    private int maxPage;
    private int pagePerContent = 5;

    public int getCurrentPage() {
        return currentPage;
    }

    public int getMaxPage() {
        return maxPage;
    }

    public BookContentPanel(MainFrame mainFrame) {
        super(mainFrame);
        bookController = new BookController();
        // books And count를 가지고 온다.
        books = bookController.findAllBookWithCount();
        maxPage = (int) Math.ceil(books.size() / pagePerContent);
        currentPage = 1;

        // ContentPanel에서 시작하는 번호와, 끝번호를 가지고 있어야 한다.
        pagingPanel = new PagingPanel(contentWidth, contentHeight, this);
        update(currentPage);
    }

    public BookContentPanel(MainFrame mainFrame, String title) {
        super(mainFrame);
        bookController = new BookController();
        // books And count를 가지고 온다.
        books = bookController.findBooksByContainsTitle(title);
        maxPage = (int) Math.ceil(books.size() / pagePerContent);
        currentPage = 1;

        // ContentPanel에서 시작하는 번호와, 끝번호를 가지고 있어야 한다.
        pagingPanel = new PagingPanel(contentWidth, contentHeight, this);
        update(currentPage);

    }

    public BookContentPanel(MainFrame mainFrame, CategoryDto categoryDto) {
        super(mainFrame);
        bookController = new BookController();
        // books And count를 가지고 온다.
        books = bookController.findBooksByContainsCategoryName(categoryDto.name());
        maxPage = (int) Math.ceil(books.size() / pagePerContent);
        currentPage = 1;

        // ContentPanel에서 시작하는 번호와, 끝번호를 가지고 있어야 한다.
        pagingPanel = new PagingPanel(contentWidth, contentHeight, this);
        update(currentPage);

    }

    // update가 호출되면 pageNum를 갱신하고 해당 페이지로 이동시킨다.
    public void update(int pageNum) {
        removeAll();
        currentPage = pageNum;
        pagingPanel.updatePagingPanel();
        updateSummaryContent();
        add(pagingPanel);
    }

    private void updateSummaryContent() {
        int start = (currentPage - 1) * pagePerContent;
        int end = currentPage * pagePerContent;
        for (int i = start; i < end; i++) {
            Book book = books.get(i);
            BookSummaryPanel bookSummaryPanel = new BookSummaryPanel(contentWidth, (contentHeight - 300) / 5, this, book);
            add(bookSummaryPanel);
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