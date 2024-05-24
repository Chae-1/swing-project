package com.booklink.ui.panel.menu;

import com.booklink.ui.frame.main.MainFrame;

import javax.swing.*;
import java.awt.*;

// MenuPanel 클래스
public class MenuPanel extends JPanel {
    public MenuPanel(int width, int height, MainFrame mainFrame) {
        setSize(new Dimension(width, height));
        setLayout(null);

        SearchPanel searchPanel = new SearchPanel();
        UserPanel userPanel = new UserPanel();
        JButton backButton = new JButton("뒤로가기");
        backButton.addActionListener((e) -> {
            mainFrame.updatePrevContent();
        });

        int searchPanelWidth = 1000;
        int searchPanelHeight = 50;

        int backButtonWidth = 100;
        int backButtonHeight = 50;

        int userPanelWidth = 400;
        int userPanelHeight = 50;

        searchPanel.setBounds(0, 0, searchPanelWidth, searchPanelHeight);
        backButton.setBounds(searchPanelWidth, 0, backButtonWidth, backButtonHeight);
        userPanel.setBounds(searchPanelWidth + backButtonWidth + 100, 0, userPanelWidth, userPanelHeight);

        add(searchPanel);
        add(backButton);
        add(userPanel);
    }
}