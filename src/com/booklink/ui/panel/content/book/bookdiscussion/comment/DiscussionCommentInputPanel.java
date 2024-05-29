package com.booklink.ui.panel.content.book.bookdiscussion.comment;

import com.booklink.controller.DiscussionCommentController;
import com.booklink.model.book.disscussion.comment.DiscussionCommentRegisterDto;
import com.booklink.model.user.exception.UserException;
import com.booklink.utils.UserHolder;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class DiscussionCommentInputPanel extends JPanel {

    private DiscussionCommentController commentController;

    public DiscussionCommentInputPanel(Long discussionId) {
        setLayout(null);
        commentController = new DiscussionCommentController();
        setPreferredSize(new Dimension(1215, 100));

        // 입력란
        JTextArea inputArea = new JTextArea();
        inputArea.setBounds(0, 0, 850, 100);
        inputArea.setLineWrap(true);
        inputArea.setWrapStyleWord(true);
        add(inputArea);

        // 확인 버튼
        JButton submitButton = new JButton("등록");
        submitButton.setBounds(981, 0, 100, 100);

        submitButton.addActionListener((event) -> {
            String text = inputArea.getText();
            try {
                commentController.registerComment(new DiscussionCommentRegisterDto(UserHolder.getId(), discussionId, LocalDateTime.now(), text));
                Container parent = this.getParent();
                if (parent instanceof DiscussionCommentPanel p) {
                    p.update(1);
                }
            } catch (UserException e) {
                // comment 예외시, 메세지를 출력
                // popUp 형태로 제공할 것.
                JOptionPane.showMessageDialog(this, e.getMessage(),
                        "Fail", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        add(submitButton);
    }
}
