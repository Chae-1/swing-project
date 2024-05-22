package com.booklink.ui.panel.menu;

import javax.swing.*;
import java.awt.*;

public class UserPanel extends JPanel {

    public UserPanel() {
        // 패널 크기 설정
        setPreferredSize(new Dimension(600, 50));

        // 레이아웃 매니저를 null로 설정하여 절대 위치 사용
        setLayout(null);

        // loginButton과 signUpButton 생성
        JButton loginButton = new JButton("Login");
        JButton signUpButton = new JButton("Sign Up");

        // 버튼 크기 설정
        int buttonWidth = 100;
        int buttonHeight = 50;

        // 컴포넌트 위치 설정
        int panelWidth = 600;
        int panelHeight = 50;

        int loginButtonX = (panelWidth - buttonWidth * 2 - 10) / 2;
        int loginButtonY = (panelHeight - buttonHeight) / 2;

        int signUpButtonX = loginButtonX + buttonWidth + 10;
        int signUpButtonY = loginButtonY;

        loginButton.setBounds(loginButtonX, loginButtonY, buttonWidth, buttonHeight);
        signUpButton.setBounds(signUpButtonX, signUpButtonY, buttonWidth, buttonHeight);

        // 패널에 컴포넌트 추가
        add(loginButton);
        add(signUpButton);
    }
}