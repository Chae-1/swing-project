package com.booklink.ui.panel.content.book.bookdiscussion.comment;

import com.booklink.model.book.disscussion.comment.DiscussionCommentDto;
import com.booklink.model.book.disscussion.comment.DiscussionCommentRegisterDto;
import com.booklink.utils.UserHolder;

import javax.swing.*;
import java.awt.*;

public class DiscussionCommentSummaryPanel extends JPanel {

    public DiscussionCommentSummaryPanel(DiscussionCommentDto discussionCommentDto) {
        setLayout(null);
        setPreferredSize(new Dimension(1215, 70));

        JLabel jLabel = new JLabel(discussionCommentDto.userName() + " / " + discussionCommentDto.regDate().toString());
        jLabel.setBounds(0, 0, 700, 30);
        add(jLabel);

        JButton removeButton = new JButton("remove");

        removeButton.setBounds(700, 0, 200, 30);
        removeButton.addActionListener((e) -> {
            try {
                if (this.getParent() instanceof DiscussionCommentPanel p) {
                    p.removeComment(discussionCommentDto.commentId(), UserHolder.getId());
                    p.update(1);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "ex.getMessage()",
                        ex.getMessage(), JOptionPane.ERROR_MESSAGE);
            }
        });

        add(removeButton);

        JButton updateButton = new JButton("update");
        updateButton.addActionListener(e -> {

        });
        updateButton.setBounds(900, 0, 200, 30);
        add(updateButton);

        JTextArea jTextArea = new JTextArea(discussionCommentDto.comment());
        jTextArea.setBounds(0, 30, 1215, 40);
        jTextArea.setLineWrap(true);
        jTextArea.setWrapStyleWord(true);
        jTextArea.setEditable(false);
        add(jTextArea);
    }
}
