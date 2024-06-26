package com.booklink.ui.panel.content.book;

import com.booklink.controller.BookController;
import com.booklink.model.book.Book;
import com.booklink.model.categories.CategoryDto;
import com.booklink.ui.frame.main.MainFrame;
import com.booklink.ui.panel.content.ContentPanel;
import com.booklink.ui.panel.content.PagingPanel;
import com.booklink.ui.panel.content.book.booksummary.BookSummaryPanel;

import java.util.List;
import java.util.Set;
// 1488, 970


public class BookContentPanel extends ContentPanel {
    private BookController bookController;
    private PagingPanel pagingPanel;

    private List<Book> books;
    private int currentPage;
    private int maxPage;
    private int pagePerContent = 5;

    @Override
    public int getCurrentPage() {
        return currentPage;
    }

    @Override
    public int getMaxPage() {
        return maxPage;
    }

    public BookContentPanel(MainFrame mainFrame) {
        super(mainFrame);
        bookController = new BookController();
        // books And count를 가지고 온다.
        books = bookController.findAllBookWithCount();
        maxPage = getMax();
        currentPage = 1;

        // ContentPanel에서 시작하는 번호와, 끝번호를 가지고 있어야 한다.
        pagingPanel = new PagingPanel(contentWidth, contentHeight, this);
        updateDisplay(currentPage);
    }

    public BookContentPanel(MainFrame mainFrame, String title) {
        super(mainFrame);
        bookController = new BookController();
        // books And count를 가지고 온다.
        books = bookController.findBooksByContainsTitle(title);
        maxPage = getMax();
        currentPage = 1;

        // ContentPanel에서 시작하는 번호와, 끝번호를 가지고 있어야 한다.
        pagingPanel = new PagingPanel(contentWidth, contentHeight, this);
        updateDisplay(currentPage);

    }

    public BookContentPanel(MainFrame mainFrame, Set<String> categoryNames) {
        super(mainFrame);
        bookController = new BookController();
        // books And count를 가지고 온다.
        books = bookController.findBooksByContainsCategoryNames(categoryNames);
        maxPage = getMax();
        currentPage = 1;
        // ContentPanel에서 시작하는 번호와, 끝번호를 가지고 있어야 한다.
        pagingPanel = new PagingPanel(contentWidth, contentHeight, this);
        updateDisplay(currentPage);
    }


    public BookContentPanel(MainFrame mainFrame, CategoryDto categoryDto) {
        super(mainFrame);
        bookController = new BookController();
        // books And count를 가지고 온다.
        books = bookController.findBooksByContainsCategoryName(categoryDto.name());
        System.out.println(books.size());
        maxPage = getMax();
        currentPage = 1;
        // ContentPanel에서 시작하는 번호와, 끝번호를 가지고 있어야 한다.
        pagingPanel = new PagingPanel(contentWidth, contentHeight, this);
        updateDisplay(currentPage);
    }

    private int getMax() {
        return Math.max(1, (int) Math.ceil((double) books.size() / pagePerContent));
    }


    // update가 호출되면 pageNum를 갱신하고 해당 페이지로 이동시킨다.
    @Override
    public void updateDisplay(int page) {
        removeAll();
        currentPage = page;
        pagingPanel.updatePagingPanel();
        int start = (currentPage - 1) * pagePerContent;
        int end = Math.min(currentPage * pagePerContent, books.size());
        for (int i = start; i < end; i++) {
            Book book = books.get(i);
            BookSummaryPanel bookSummaryPanel = new BookSummaryPanel(contentWidth,
                    (contentHeight - 300) / 5, this,
                    book);
            add(bookSummaryPanel);
        }
        add(pagingPanel);
        revalidate();
        repaint();
    }
}
