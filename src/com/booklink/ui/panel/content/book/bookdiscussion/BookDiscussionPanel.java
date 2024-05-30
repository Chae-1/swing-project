package com.booklink.ui.panel.content.book.bookdiscussion;

import com.booklink.controller.BookDiscussionController;
import com.booklink.model.book.Book;
import com.booklink.model.book.disscussion.BookDiscussionDto;
import com.booklink.model.book.disscussion.BookDiscussionRegisterForm;
import com.booklink.ui.frame.main.MainFrame;
import com.booklink.ui.panel.content.ContentPanel;
import com.booklink.ui.panel.content.PagingPanel;

import java.util.List;


// 1170, 920
// 1170, 920 - 150
// 1170, 900 - 130
// 1170, 770

public class BookDiscussionPanel extends ContentPanel {
    private BookDiscussionController bookDiscussionController = new BookDiscussionController();
    private PagingPanel pagingPanel;
    private List<BookDiscussionDto> bookDiscussions;
    private BookDiscussionTitle bookDiscussionTitle;
    private MainFrame mainFrame;
    private int currentPage;
    private int maxPage;
    private int pagePerContent = 5;

    public BookDiscussionPanel(MainFrame mainFrame, Book book) {
        super(mainFrame);
        init();
        currentPage = 1;
        this.mainFrame = mainFrame;

        updateDiscussion(book.getId(), getCurrentPage());
        bookDiscussionTitle = new BookDiscussionTitle(contentWidth, book, this);

        // 책 제목, 가운데 정렬,
        pagingPanel = new PagingPanel(contentWidth, 100, this);
        updateDisplay(currentPage);
    }

    public void updateDiscussion(Long bookId, int currentPage) {
        bookDiscussions = bookDiscussionController.findAllDiscussionAboutBook(bookId);
        this.currentPage = currentPage;
        System.out.println(bookDiscussions.size());
        double ceil = (double)bookDiscussions.size() / pagePerContent;
        System.out.println("before : "  + ceil);
        maxPage = Math.max(1, (int) Math.ceil(ceil));
    }

    @Override
    public int getMaxPage() {
        return maxPage;
    }

    @Override
    protected int getCurrentPage() {
        return currentPage;
    }

    @Override
    public void updateDisplay(int page) {
        removeAll();
        bookDiscussionTitle.setBounds(0, 0, contentWidth, 100);
        add(bookDiscussionTitle);

        currentPage = page;
        pagingPanel.updatePagingPanel();
        int start = (currentPage - 1) * pagePerContent;
        int end = Math.min(currentPage * pagePerContent, bookDiscussions.size());
        int idx = 0;
        for (int i = start; i < end; i++) {
            System.out.println(contentHeight);
            BookDiscussionDto bookDiscussion = bookDiscussions.get(i);
            BookDiscussionSummaryPanel bookSummaryPanel = new BookDiscussionSummaryPanel(contentWidth,
                    100, this, bookDiscussion);
            bookSummaryPanel.setBounds(0, 100 + 100 * idx++, contentWidth, 100);
            add(bookSummaryPanel);
        }
        pagingPanel.setBounds(0, 700, contentWidth, 100);
        add(pagingPanel);
        revalidate();
        repaint();
    }

    @Override
    protected void init() {
        setLayout(null);
        setSize(contentWidth, contentHeight);
    }

    public void addDiscussion(BookDiscussionRegisterForm bookDiscussionRegisterForm) {
        bookDiscussionController.addDiscussion(bookDiscussionRegisterForm);
    }

    public void changeDetailDisc(BookDiscussionDto bookDiscussion) {
        mainFrame.changeCurrentContent(new BookDiscussionDetailPanel(mainFrame, bookDiscussion));
    }
}
