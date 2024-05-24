package com.booklink.ui.panel.menu;

import com.booklink.ui.frame.main.MainFrame;
import com.booklink.ui.panel.content.book.BookContentPanel;

import javax.swing.*;
import java.awt.*;

public class SearchPanel extends JPanel {

    public SearchPanel() {
        // 패널 크기 설정

        // 레이아웃 매니저를 null로 설정하여 절대 위치 사용
        setLayout(null);

        // searchField와 searchButton 생성
        JTextField searchField = new JTextField();
        JButton searchButton = new JButton("검색");

        // 컴포넌트 크기 설정
        int fieldWidth = 500;
        int fieldHeight = 50;

        int buttonWidth = 100;
        int buttonHeight = 50;

        // 패널 크기 설정

        // 컴포넌트 위치 설정
        searchField.setBounds(0, 0, fieldWidth, fieldHeight);
        searchButton.setBounds(fieldWidth, 0, buttonWidth, buttonHeight);
        searchButton.addActionListener((e) -> {
            Window windowAncestor = SwingUtilities.getWindowAncestor(SearchPanel.this);
            if (windowAncestor instanceof MainFrame mainFrame) {
                String title = searchField.getText();
                mainFrame.changeCurrentContent(new BookContentPanel(mainFrame, title));
            }
        });
        // 패널에 컴포넌트 추가
        add(searchField);
        add(searchButton);
    }
}
