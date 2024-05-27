package com.booklink.ui.frame.main;

import com.booklink.model.user.User;
import com.booklink.ui.panel.content.book.BookContentPanel;
import com.booklink.ui.panel.content.CategoryPanel;
import com.booklink.ui.panel.content.ContentPanel;
import com.booklink.ui.panel.menu.MenuPanel;
import com.booklink.ui.panel.menu.MyPagePanel;

import javax.swing.*;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class MainFrame extends JFrame {
    private MenuPanel menuPanel;
    private CategoryPanel categoryPanel;
    private ContentPanel contentPanel;

    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1080;

    private Deque<ContentPanel> prevPanelDeque = new LinkedList<>();
    private User userHolder;

    public MainFrame() {
        // 프레임 제목 설정
        setTitle("Main Frame with Panels");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT); // 전체 프레임 크기 설정
        setResizable(false); // 프레임 고정

        init(new BookContentPanel(this));
    }

    private void init(ContentPanel contentPanel) {
        // 레이아웃 매니저를 null로 설정하여 절대 위치 사용
        setLayout(null);

        // MenuPanel 인스턴스 생성
        // menuPanel은 1920 * 50 최상단에 위치
        menuPanel = new MenuPanel(WIDTH, 50);
        // 카테고리 패널은
        int categoryWidth = WIDTH / 4 - 250;
        int categoryHeight = HEIGHT - 50 - 100;
        categoryPanel = new CategoryPanel(categoryWidth, categoryHeight, this);

        // ContentPanel 인스턴스 생성
        int contentWidth = WIDTH - (WIDTH / 4) - 20 - 250;
        int contentHeight = HEIGHT - 50 - 100;
        this.contentPanel = contentPanel;


        // 프레임에 패널 추가
        add(menuPanel).setBounds(0, 0, WIDTH, 50);
        add(categoryPanel).setBounds(0, 50, categoryWidth, categoryHeight);
        add(this.contentPanel).setBounds(categoryWidth, 50, contentWidth, contentHeight);

        // 프레임을 화면 중앙에 표시
        setLocationRelativeTo(null);
    }


    // 페이지를 갱신하기 이전 페이지를 queue 에 저장한다.
    public void loadPrevContent(ContentPanel contentPanel) {
        prevPanelDeque.add(contentPanel);
    }

    public static void main(String[] args) {
        // Event Dispatch Thread에서 실행
        SwingUtilities.invokeLater(() -> {
            // MainFrame 인스턴스 생성 및 표시
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }

    public void changeCurrentContent(ContentPanel contentPanel) {
        remove(this.contentPanel);
        this.contentPanel = contentPanel;
        int categoryWidth = WIDTH / 4 - 250;
        int contentWidth = WIDTH - (WIDTH / 4) - 20 - 250;
        int contentHeight = HEIGHT - 50 - 100;
        add(this.contentPanel).setBounds(categoryWidth, 50, contentWidth, contentHeight);
        // 레이아웃을 재검증하고 다시 그리기
        revalidate();
        repaint();
    }

    public void updatePrevContent() {
        changeCurrentContent(prevPanelDeque.pop());
    }

    public void showMyPage(){
        MyPagePanel myPagePanel = new MyPagePanel(this);
        loadPrevContent(contentPanel);
        changeCurrentContent(myPagePanel);
    }
}
