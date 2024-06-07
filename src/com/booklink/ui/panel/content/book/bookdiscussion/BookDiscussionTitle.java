package com.booklink.ui.panel.content.book.bookdiscussion;

import com.booklink.model.book.Book;
import com.booklink.model.book.disscussion.BookDiscussionRegisterForm;
import com.booklink.utils.UserHolder;

import javax.swing.*;

public class BookDiscussionTitle extends JPanel {

    private JLabel titleLabel;
    private JButton addDiscussion;
    private BookDiscussionPanel bookDiscussionPanel;

    public BookDiscussionTitle(int contentWidth, Book book, BookDiscussionPanel panel) {
        setLayout(null);
        this.bookDiscussionPanel = panel;

        System.out.println(contentWidth);
        setSize(contentWidth, 100);
        titleLabel = new JLabel(book.getTitle() + "  " + "에 대한 의견을 공유해 주세요");
        titleLabel.setBounds(0, 0, contentWidth - 300, 100);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        addDiscussion = new JButton("의견추가");
        addDiscussion.setBounds(contentWidth - 300, 0, 300, 100);
        addDiscussion.addActionListener((e) -> {
            showAddDiscussionDialog(book.getId());
        });

        add(titleLabel);
        add(addDiscussion);
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
                bookDiscussionPanel.addDiscussion(new BookDiscussionRegisterForm(bookId, UserHolder.getId(), discussionTitle, discussionContent));
                bookDiscussionPanel.updateDiscussion(bookId, bookDiscussionPanel.getCurrentPage());
                bookDiscussionPanel.updateDisplay(bookDiscussionPanel.getCurrentPage());
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
