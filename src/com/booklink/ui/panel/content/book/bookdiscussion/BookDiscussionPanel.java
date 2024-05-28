package com.booklink.ui.panel.content.book.bookdiscussion;

import com.booklink.controller.BookController;
import com.booklink.controller.BookDiscussionController;
import com.booklink.controller.OrderController;
import com.booklink.model.book.Book;
import com.booklink.model.book.disscussion.BookDiscussion;
import com.booklink.model.book.disscussion.BookDiscussionRegisterForm;
import com.booklink.ui.frame.main.MainFrame;
import com.booklink.ui.panel.content.ContentPanel;
import com.booklink.ui.panel.content.PagingPanel;
import com.booklink.ui.panel.content.book.booksummary.BookSummaryPanel;
import com.booklink.utils.UserHolder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


// 1170, 920
// 1170, 920 - 150
// 1170, 900 - 130
// 1170, 770

public class BookDiscussionPanel extends ContentPanel {
    private BookDiscussionController bookDiscussionController = new BookDiscussionController();
    private PagingPanel pagingPanel;
    private List<BookDiscussion> bookDiscussions;
    private int currentPage;
    private int maxPage;
    private int pagePerContent = 10;
    private JLabel titleLabel;
    private JButton addDiscussion;

    public BookDiscussionPanel(MainFrame mainFrame, Book book) {
        super(mainFrame);
        bookDiscussions = bookDiscussionController.findAllDiscussionAboundBook(book.getId());
        currentPage = 1;
        maxPage = Math.max(1, (int) Math.ceil(bookDiscussions.size() / pagePerContent));

        titleLabel = new JLabel(book.getTitle() + "  " + "에 대한 의견을 공유해 주세요");
        titleLabel.setBounds(0, 0, contentWidth - 300, 100);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        addDiscussion = new JButton("의견추가");
        addDiscussion.addActionListener((e) -> {
            showAddDiscussionDialog(book.getId());
        });
        addDiscussion.setBounds(contentWidth - 300, 0, contentWidth - 300, 50);
        // 책 제목, 가운데 정렬,
        pagingPanel = new PagingPanel(contentWidth, 100, this);
        update(currentPage);

        mainFrame.changeCurrentContent(this);
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
    protected void update(int page) {
        removeAll();
        add(titleLabel);
        add(addDiscussion);
        currentPage = page;
        pagingPanel.updatePagingPanel();
        int start = (currentPage - 1) * pagePerContent;
        int end = Math.min(currentPage * pagePerContent, bookDiscussions.size());
        for (int i = start; i < end; i++) {
            BookDiscussion bookDiscussion = bookDiscussions.get(i);
            BookDiscussionTitlePanel bookSummaryPanel = new BookDiscussionTitlePanel(contentWidth,
                    (contentHeight - 300) / 5, this, bookDiscussion);
            add(bookSummaryPanel);
        }
        add(pagingPanel);
    }

    @Override
    protected void init() {
        setLayout(null);
        setSize(contentWidth, contentHeight);
    }


    private void showAddDiscussionDialog(Long bookId) {
        JDialog dialog = new JDialog();
        dialog.setTitle("의견 추가");
        dialog.setSize(1440, 900);
        dialog.setLayout(null);

        // 의견 제목 입력 라벨
        JLabel titleLabel = new JLabel("의견 제목:");
        titleLabel.setBounds(10, 10, 140, 25);
        dialog.add(titleLabel);

        // 의견 제목 입력 창
        JTextField titleField = new JTextField();
        titleField.setBounds(160, 10, 1270, 25); // 총 가로 크기 1440 - 라벨 크기(140) - 라벨과 입력창 사이 간격(20)
        dialog.add(titleField);

        // 의견 내용 입력 라벨
        JLabel contentLabel = new JLabel("의견 내용:");
        contentLabel.setBounds(10, 50, 140, 25);
        dialog.add(contentLabel);

        // 의견 내용 입력 창
        JTextArea contentArea = new JTextArea();
        contentArea.setBounds(10, 80, 1420, 600);
        dialog.add(contentArea);

        // 등록 버튼
        JButton submitButton = new JButton("추가");
        submitButton.setBounds(570, 750, 300, 100); // 가로 중앙 정렬: (1440 - 300) / 2 = 570
        submitButton.addActionListener(e -> {
            String discussionTitle = titleField.getText();
            String discussionContent = contentArea.getText();
            if (!discussionTitle.isEmpty() && !discussionContent.isEmpty()) {
                bookDiscussionController.addDiscussion(new BookDiscussionRegisterForm(bookId, UserHolder.getId(), discussionTitle, discussionContent));
                bookDiscussions = bookDiscussionController.findAllDiscussionAboundBook(bookId);
                update(currentPage);
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "의견 제목과 내용을 입력해 주세요", "오류", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.add(submitButton);
        dialog.setLocationRelativeTo(null);
        dialog.setModal(true);
        dialog.setVisible(true);
    }
}
