package com.booklink.ui.panel.content.book.bookdetail.comment;

import com.booklink.model.book.comments.CommentSummaryDto;
import com.booklink.utils.UserHolder;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class CommentSummaryPanel extends JPanel {

    public CommentSummaryPanel(CommentSummaryDto discussionCommentDto) {
        setLayout(null);
        setPreferredSize(new Dimension(1215, 70));

        JLabel jLabel = new JLabel(discussionCommentDto.userName() + " / " + discussionCommentDto.regDate().toString() + " / " + discussionCommentDto.rating());
        jLabel.setBounds(0, 0, 700, 30);
        add(jLabel);

        JButton removeButton = new JButton("remove");
        removeButton.setBounds(700, 0, 200, 30);
        removeButton.addActionListener((e) -> {
            try {
                if (this.getParent() instanceof CommentPanel p) {
                    p.removeComment(discussionCommentDto.commentId(), UserHolder.getId());
                    p.updateDisplay(1);
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "An error occurred: " + ex.getMessage(),
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
