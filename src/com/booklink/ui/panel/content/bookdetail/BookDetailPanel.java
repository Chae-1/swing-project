package com.booklink.ui.panel.content.bookdetail;

import com.booklink.model.book.Book;
import com.booklink.ui.panel.content.ContentPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class BookDetailPanel extends JPanel {
    public BookDetailPanel(Book book, ContentPanel parentPanel) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel(book.getTitle());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel authorLabel = new JLabel("Author: " + book.getAuthor());
        authorLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        authorLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JTextArea descriptionArea = new JTextArea(book.getDescription());
        descriptionArea.setFont(new Font("Arial", Font.PLAIN, 14));
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(descriptionArea);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentPanel.showBookList();
            }
        });

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(titleLabel, BorderLayout.NORTH);
        topPanel.add(authorLabel, BorderLayout.CENTER);
        topPanel.add(backButton, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
}