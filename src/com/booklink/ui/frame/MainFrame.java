package com.booklink.ui.frame;

import com.booklink.ui.panel.content.CategoryPanel;
import com.booklink.ui.panel.content.ContentPanel;
import com.booklink.ui.panel.menu.MenuPanel;
import com.booklink.ui.panel.menu.SearchPanel;
import com.booklink.ui.panel.menu.UserPanel;

import java.awt.Dimension;
import javax.swing.*;

public class MainFrame extends JFrame {
    private MenuPanel menuPanel;
    private JScrollPane scrollPane;
    private CategoryPanel categoryPanel;

    public MainFrame() {
        // 프레임 제목 설정
        setTitle("Main Frame with Panels");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1440, 1024); // 전체 프레임 크기 설정

        // 레이아웃 매니저를 null로 설정하여 절대 위치 사용
        setLayout(null);

        // MenuPanel 인스턴스 생성
        menuPanel = new MenuPanel();
        categoryPanel = new CategoryPanel();
        scrollPane = new JScrollPane(new ContentPanel());
        scrollPane.setPreferredSize(new Dimension(1008, 970));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        // 패널의 위치와 크기 설정
        menuPanel.setBounds(0, 0, 1440, 50);
        categoryPanel.setBounds(0, 50, 432, 970);
        scrollPane.setBounds(432, 50, 1008, 970);

        // 프레임에 패널 추가
        add(menuPanel);
        add(categoryPanel);
        add(scrollPane);

        // 프레임을 화면 중앙에 표시
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        // Event Dispatch Thread에서 실행
        SwingUtilities.invokeLater(() -> {
            // MainFrame 인스턴스 생성 및 표시
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}