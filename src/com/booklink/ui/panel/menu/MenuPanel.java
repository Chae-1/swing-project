package com.booklink.ui.panel.menu;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {

    public MenuPanel() {
        // 패널 크기 설정
        setPreferredSize(new Dimension(1440, 50));

        // BorderLayout으로 설정
        setLayout(new BorderLayout());

        // SearchPanel과 UserPanel 인스턴스 생성
        SearchPanel searchPanel = new SearchPanel();
        UserPanel userPanel = new UserPanel();

        // MenuPanel에 SearchPanel을 가운데에 추가
        add(searchPanel, BorderLayout.CENTER);
        // MenuPanel에 UserPanel을 오른쪽에 추가
        add(userPanel, BorderLayout.EAST);
    }
}
