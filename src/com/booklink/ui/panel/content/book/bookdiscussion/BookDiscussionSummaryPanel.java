package com.booklink.ui.panel.content.book.bookdiscussion;

import com.booklink.model.book.disscussion.BookDiscussionDto;
import com.booklink.ui.panel.content.book.bookdiscussion.comment.DiscussionCommentPanel;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//
public class BookDiscussionSummaryPanel extends JPanel {

    public BookDiscussionSummaryPanel(int contentWidth, int contentHeight, BookDiscussionPanel bookDiscussionPanel, BookDiscussionDto bookDiscussion) {
        // 제목, 작성자 명, 컨텐츠 일부
        setLayout(null);
        setSize(contentWidth, contentHeight);

        JLabel titleLabel = new JLabel(bookDiscussion.title());
        titleLabel.setBounds(0, 0, contentWidth, contentHeight / 3);
        titleLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                bookDiscussionPanel.changeDetailDisc(bookDiscussion);
            }
        });
        add(titleLabel);
        System.out.println(titleLabel.getText());

        JLabel authorLabel = new JLabel(bookDiscussion.userName()); // 작성자 이름을 표시
        authorLabel.setBounds(0, contentHeight / 3, contentWidth, contentHeight / 3);
        add(authorLabel);
        System.out.println(authorLabel.getText());

        JTextArea contentLabel = new JTextArea(bookDiscussion.content());
        contentLabel.setBounds(0,  2 * contentHeight / 3, contentWidth, contentHeight / 3);
        contentLabel.setLineWrap(true);
        contentLabel.setWrapStyleWord(true);
        contentLabel.setEditable(false); // 내용 창을 읽기 전용으로 설정
        add(contentLabel);
        System.out.println(contentLabel.getText());
    }

}
