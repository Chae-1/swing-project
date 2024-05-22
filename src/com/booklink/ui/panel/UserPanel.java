package com.booklink.ui.panel;

import javax.swing.*;
import java.awt.*;

public class UserPanel extends JPanel {

    public UserPanel() {
        // 패널 크기 설정
        setPreferredSize(new Dimension(600, 80));

        // 레이아웃 매니저를 null로 설정하여 절대 위치 사용
        setLayout(null);

        // loginButton과 signUpButton 생성
        JButton loginButton = new JButton("Login");
        JButton signUpButton = new JButton("Sign Up");

        // 컴포넌트 크기와 위치 설정
        Dimension buttonSize = loginButton.getPreferredSize();

        int panelWidth = 460;
        int panelHeight = 80;

        int loginButtonX = (panelWidth - (int) buttonSize.getWidth() * 2 - 10) / 2;
        int loginButtonY = (panelHeight - (int) buttonSize.getHeight()) / 2;

        int signUpButtonX = loginButtonX + (int) buttonSize.getWidth() + 10;
        int signUpButtonY = loginButtonY;

        loginButton.setBounds(loginButtonX, loginButtonY, (int) buttonSize.getWidth(), (int) buttonSize.getHeight());
        signUpButton.setBounds(signUpButtonX, signUpButtonY, (int) buttonSize.getWidth(), (int) buttonSize.getHeight());

        // 패널에 컴포넌트 추가
        add(loginButton);
        add(signUpButton);
    }
}