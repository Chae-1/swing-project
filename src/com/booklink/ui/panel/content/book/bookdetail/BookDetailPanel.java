package com.booklink.ui.panel.content.book.bookdetail;

import com.booklink.model.book.Book;
import com.booklink.ui.frame.main.MainFrame;
import com.booklink.ui.panel.content.ContentPanel;

import javax.swing.*;
import java.awt.*;

public class BookDetailPanel extends ContentPanel {
    public BookDetailPanel(MainFrame mainFrame, Book book) {
        super(mainFrame);
        JLabel titleLabel = new JLabel(book.getTitle());
        titleLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel);

    }
//    public BookDetailPanel(Book book, ContentPanel parentPanel) {
//        setLayout(new BorderLayout());
//        setBackground(Color.WHITE);
//
//        JLabel titleLabel = new JLabel(book.getTitle());
//        titleLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 24));
//        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
//
//        JLabel authorLabel = new JLabel("Author: " + book.getAuthor());
//        authorLabel.setFont(new Font("Malgun Gothic", Font.PLAIN, 18));
//        authorLabel.setHorizontalAlignment(SwingConstants.CENTER);
//
//        JTextArea descriptionArea = new JTextArea(book.getDescription());
//        descriptionArea.setFont(new Font("Malgun Gothic", Font.PLAIN, 14));
//        descriptionArea.setLineWrap(true);
//        descriptionArea.setWrapStyleWord(true);
//        descriptionArea.setEditable(false);
//
//        JScrollPane scrollPane = new JScrollPane(descriptionArea);
//
//        JButton backButton = new JButton("Back");
//        backButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                parentPanel.showBookList();
//            }
//        });
//
//        JPanel topPanel = new JPanel(new BorderLayout());
//        topPanel.add(titleLabel, BorderLayout.NORTH);
//        topPanel.add(authorLabel, BorderLayout.CENTER);
//        topPanel.add(backButton, BorderLayout.SOUTH);
//
//        add(topPanel, BorderLayout.NORTH);
//        add(scrollPane, BorderLayout.CENTER);
//    }
}