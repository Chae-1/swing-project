package com.booklink.ui.panel.content.book.bookdetail.comment;

import com.booklink.model.book.comments.CommentDto;
import com.booklink.utils.UserHolder;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class CommentSummaryPanel extends JPanel {

    public CommentSummaryPanel(CommentDto commentDto) {
        setLayout(null);
        setPreferredSize(new Dimension(1215, 70));

        JLabel jLabel = new JLabel(commentDto.name() + " / " + commentDto.regDate().toString() + " / " + commentDto.rating());
        jLabel.setBounds(0, 0, 700, 30);
        add(jLabel);

        // 415
        JButton removeButton = new JButton("remove");
        removeButton.setBounds(700, 0, 200, 30);
        removeButton.addActionListener((e) -> {
            try {
                if (this.getParent() instanceof CommentPanel p) {
                    p.removeComment(commentDto.commentId(), UserHolder.getId());
                    p.update(1);
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "An error occurred: " + ex.getMessage(),
                        ex.getMessage(), JOptionPane.ERROR_MESSAGE);
            }
        });

        add(removeButton);

        JButton updateButton = new JButton("update");
        updateButton.setBounds(900, 0, 200, 30);
        add(updateButton);

        JTextArea jTextArea = new JTextArea(commentDto.comment());
        jTextArea.setBounds(0, 30, 1215, 40);
        jTextArea.setLineWrap(true);
        jTextArea.setWrapStyleWord(true);
        jTextArea.setEditable(false);
        add(jTextArea);

    }
}
