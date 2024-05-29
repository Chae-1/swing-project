package com.booklink.ui.panel.menu;

import com.booklink.ui.frame.main.MainFrame;

import com.booklink.ui.panel.content.book.BookContentPanel;
import com.booklink.ui.panel.content.book.bookregister.BookRegisterDialog;
import com.booklink.utils.UserHolder;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// MenuPanel 클래스
public class MenuPanel extends JPanel {

    public MenuPanel(int width, int height) {
        setSize(new Dimension(width, height));
        setLayout(null);

        // 로고 추가
        JLabel logoLabel = new JLabel("BookLink");
        logoLabel.setFont(new Font("Arial", Font.BOLD, 24)); // 원하는 폰트와 크기로 설정
        logoLabel.setBounds(10, 0, 250, 50);


        // 기존 패널 및 버튼.
        SearchPanel searchPanel = new SearchPanel();
        UserPanel userPanel = new UserPanel();

        JButton addBookButton = new JButton("도서 등록");

        addBookButton.addActionListener((e) -> {
            MainFrame mainFrame = (MainFrame) SwingUtilities.getWindowAncestor(this);

            if (UserHolder.isRoot()) {
                BookRegisterDialog dialog = new BookRegisterDialog(mainFrame);
                dialog.setLocationRelativeTo(null); ///수정필요
                dialog.setVisible(true);
            } else {
                JOptionPane optionPane = new JOptionPane(
                        "접근 권한이 없습니다.",
                        JOptionPane.WARNING_MESSAGE
                );
                JDialog dialog = optionPane.createDialog(mainFrame, "경고");
                dialog.setLocationRelativeTo(mainFrame); /// 다이얼로그 화면 중앙에 표시.
                dialog.setVisible(true);
            }
        });

        int logoWidth = 200;
        int searchPanelWidth = 600; // 검색창을 오른쪽으로 이동
        int searchPanelHeight = 50;

//        int searchPanelWidth = 900;
//        int searchPanelHeight = 50;

        int addBookButtonWidth = 100;
        int addBookButtonHeight = 50;

        int backButtonWidth = 100;
        int backButtonHeight = 50;

        int userPanelWidth = 300;
        int userPanelHeight = 50;


        logoLabel.setBounds(10, 0, logoWidth, 50); // 로고 위치 설정
        searchPanel.setBounds(logoWidth + 20, 0, searchPanelWidth, searchPanelHeight);
        addBookButton.setBounds(logoWidth + searchPanelWidth + 30, 0, addBookButtonWidth, addBookButtonHeight);
        userPanel.setBounds(logoWidth + searchPanelWidth + addBookButtonWidth + backButtonWidth + 50, 0, userPanelWidth, userPanelHeight);

        add(logoLabel); // 로고 추가
        add(searchPanel);
        add(addBookButton);
        add(userPanel);
    }
}