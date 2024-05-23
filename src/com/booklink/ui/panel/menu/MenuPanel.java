package com.booklink.ui.panel.menu;

import javax.swing.*;
import java.awt.*;

// MenuPanel 클래스
public class MenuPanel extends JPanel {
    public MenuPanel(int width, int height) {
        setPreferredSize(new Dimension(1920, 50));
        setLayout(null);

        SearchPanel searchPanel = new SearchPanel();
        UserPanel userPanel = new UserPanel();

        int searchPanelWidth = 1000;
        int searchPanelHeight = 50;
        int userPanelWidth = 800;
        int userPanelHeight = 50;

        int searchPanelX = (1920 - searchPanelWidth - userPanelWidth - 20) / 2;
        int searchPanelY = (50 - searchPanelHeight) / 2;

        int userPanelX = 1920 - userPanelWidth - 10;
        int userPanelY = (50 - userPanelHeight) / 2;

        searchPanel.setBounds(0, 0, searchPanelWidth, searchPanelHeight);
        userPanel.setBounds(searchPanelWidth, 0, userPanelWidth, userPanelHeight);

        add(searchPanel);
        add(userPanel);
    }
}