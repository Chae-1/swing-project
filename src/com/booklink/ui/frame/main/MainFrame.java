package com.booklink.ui.frame.main;

import com.booklink.ui.panel.content.CategoryPanel;
import com.booklink.ui.panel.content.ContentPanel;
import com.booklink.ui.panel.menu.MenuPanel;

import javax.swing.*;

public class MainFrame extends JFrame {
    private MenuPanel menuPanel;
    private CategoryPanel categoryPanel;
    private ContentPanel contentPanel;
    private static int WIDTH = 1920;
    private static int HEIGHT = 1080;

    public MainFrame() {
        // 프레임 제목 설정
        setTitle("Main Frame with Panels");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT); // 전체 프레임 크기 설정

        // 레이아웃 매니저를 null로 설정하여 절대 위치 사용
        setLayout(null);

        // MenuPanel 인스턴스 생성
        // menuPanel은 1920 * 50 최상단에 위치
        menuPanel = new MenuPanel(WIDTH, 50);
        // 카테고리 패널은
        int categoryWidth = WIDTH / 4 - 250;
        int categroyHeight = HEIGHT - 50 - 100;
        categoryPanel = new CategoryPanel(categoryWidth, categroyHeight);

        // ContentPanel 인스턴스 생성
        int contentWidth = WIDTH - (WIDTH / 4) - 20 - 250;
        int contentHeight = HEIGHT - 50 - 100;
        contentPanel = new ContentPanel(contentWidth, contentHeight);


        // 프레임에 패널 추가
        add(menuPanel).setBounds(0, 0, WIDTH, 50);
        add(categoryPanel).setBounds(0, 50, categoryWidth, categroyHeight);
        add(contentPanel).setBounds(categoryWidth, 50, contentWidth, contentHeight);

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
