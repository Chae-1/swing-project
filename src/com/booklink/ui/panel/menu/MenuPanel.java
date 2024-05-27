package com.booklink.ui.panel.menu;

import com.booklink.ui.frame.main.MainFrame;

import com.booklink.ui.panel.content.book.bookregister.BookRegisterDialog;
import com.booklink.utils.UserHolder;
import javax.swing.*;
import java.awt.*;

// MenuPanel 클래스
public class MenuPanel extends JPanel {
    public MenuPanel(int width, int height) {
        setSize(new Dimension(width, height));
        setLayout(null);

        SearchPanel searchPanel = new SearchPanel();
        UserPanel userPanel = new UserPanel();

        JButton addBookButton = new JButton("도서 등록");
        addBookButton.addActionListener((e) -> {
            MainFrame mainFrame = (MainFrame) SwingUtilities.getWindowAncestor(this);
            if (UserHolder.isRoot()) {
                BookRegisterDialog dialog = new BookRegisterDialog(mainFrame);
                dialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "접근 권한이 없습니다.", "경고", JOptionPane.WARNING_MESSAGE);
            }
        });

        JButton backButton = new JButton("뒤로가기");
        backButton.addActionListener((e) -> {
            if (this.getParent() instanceof MainFrame mainFrame) {
                mainFrame.updatePrevContent();
            }
        });

        int searchPanelWidth = 900;
        int searchPanelHeight = 50;

        int addBookButtonWidth = 100;
        int addBookButtonHeight = 50;

        int backButtonWidth = 100;
        int backButtonHeight = 50;

        int userPanelWidth = 300;
        int userPanelHeight = 50;

        searchPanel.setBounds(0, 0, searchPanelWidth, searchPanelHeight);
        addBookButton.setBounds(searchPanelWidth, 0, addBookButtonWidth, addBookButtonHeight);
        backButton.setBounds(searchPanelWidth + addBookButtonWidth, 0, backButtonWidth, backButtonHeight);

        userPanel.setBounds(searchPanelWidth + backButtonWidth + addBookButtonWidth, 0, userPanelWidth, userPanelHeight);

        add(searchPanel);
        add(addBookButton);
        add(backButton);
        add(userPanel);
    }
}