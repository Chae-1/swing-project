package com.booklink.ui.panel;

import javax.swing.*;
import java.awt.*;

public class SearchPanel extends JPanel {

    public SearchPanel() {
        // 패널 크기 설정
        setPreferredSize(new Dimension(610, 90));

        // 레이아웃 매니저를 null로 설정하여 절대 위치 사용
        setLayout(null);

        // searchField와 searchButton 생성
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");

        // 컴포넌트 크기와 위치 설정
        Dimension fieldSize = searchField.getPreferredSize();
        Dimension buttonSize = searchButton.getPreferredSize();

        int panelWidth = 610;
        int panelHeight = 90;

        int fieldX = (panelWidth - (int) fieldSize.getWidth() - (int) buttonSize.getWidth() - 10) / 2;
        int fieldY = (panelHeight - (int) fieldSize.getHeight()) / 2;

        int buttonX = fieldX + (int) fieldSize.getWidth() + 10;
        int buttonY = fieldY;

        searchField.setBounds(fieldX, fieldY, (int) fieldSize.getWidth(), (int) fieldSize.getHeight());
        searchButton.setBounds(buttonX, buttonY, (int) buttonSize.getWidth(), (int) buttonSize.getHeight());

        // 패널에 컴포넌트 추가
        add(searchField);
        add(searchButton);
    }
}