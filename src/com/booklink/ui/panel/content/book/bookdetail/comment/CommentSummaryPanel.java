package com.booklink.ui.panel.content.book.bookdetail.comment;

import com.booklink.model.book.comments.CommentDto;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class CommentSummaryPanel extends JPanel {
    public CommentSummaryPanel(CommentDto commentDto) {
        setLayout(null);
        setPreferredSize(new Dimension(1215, 100));

        JLabel jLabel = new JLabel(commentDto.name() + " / " + commentDto.regDate().toString() + " / " + commentDto.rating());
        jLabel.setBounds(0, 0, 1215, 30);
        add(jLabel);

        JTextArea jTextArea = new JTextArea(commentDto.comment());
        jTextArea.setBounds(0, 30, 1215, 70);
        jTextArea.setLineWrap(true);
        jTextArea.setWrapStyleWord(true);
        jTextArea.setEditable(false);
        add(jTextArea);

    }
}
