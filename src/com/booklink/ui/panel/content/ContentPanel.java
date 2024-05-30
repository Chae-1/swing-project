package com.booklink.ui.panel.content;

import com.booklink.ui.frame.main.MainFrame;

import java.awt.*;
import javax.swing.*;
// 1170, 920

public abstract class ContentPanel extends JPanel {
    protected int contentWidth = MainFrame.WIDTH - (MainFrame.WIDTH / 4) - 20 - 250;
    protected int contentHeight = MainFrame.HEIGHT - 50 - 100;

    private MainFrame mainFrame;

    public ContentPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    protected void init() {
        setLayout(new FlowLayout());
        setSize(contentWidth, contentHeight);
        setBackground(Color.LIGHT_GRAY);
    }


    protected abstract int getMaxPage();
    protected abstract int getCurrentPage();
    protected abstract void updateDisplay(int page);
}
