package com.booklink.ui.panel.content;

import com.booklink.model.book.Book;
import com.booklink.ui.frame.main.MainFrame;
import com.booklink.ui.panel.content.book.bookdetail.BookDetailPanel;

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

    // 상위
    public void moveOtherPanel(Book contentBook) {
        mainFrame.loadPrevContent(this);
        // BookDetailPanel을 생성해서 mainFrame으로 전달하면 된다.w\
        mainFrame.changeCurrentContent(new BookDetailPanel(mainFrame, contentBook));
    }

    protected abstract int getMaxPage();
    protected abstract int getCurrentPage();
    protected abstract void update(int page);
}
