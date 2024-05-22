package com.booklink.ui.frame;

import com.booklink.ui.panel.MenuPanel;
import com.booklink.ui.panel.SearchPanel;
import com.booklink.ui.panel.UserPanel;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        // 프레임 제목 설정
        setTitle("Main Frame with Menu Panel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1440, 1024); // 전체 프레임 크기 설정

        // BorderLayout을 사용하여 레이아웃 설정
        setLayout(new BorderLayout());
        // MenuPanel 인스턴스 생성
        MenuPanel menuPanel = new MenuPanel();
        // MenuPanel을 최상단(North)에 추가
        add(menuPanel, BorderLayout.NORTH);
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