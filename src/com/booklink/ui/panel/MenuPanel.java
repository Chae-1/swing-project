package com.booklink.ui.panel;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {

    public MenuPanel() {
        // 패널 크기 설정
        setPreferredSize(new Dimension(1440, 90));

        // 레이아웃 매니저를 null로 설정하여 절대 위치 사용
        setLayout(new BorderLayout());

        // SearchPanel과 UserPanel 인스턴스 생성
        SearchPanel searchPanel = new SearchPanel();
        UserPanel userPanel = new UserPanel();

        // 패널에 컴포넌트 추가
        add(searchPanel, BorderLayout.CENTER);
        add(userPanel, BorderLayout.EAST);
    }
}