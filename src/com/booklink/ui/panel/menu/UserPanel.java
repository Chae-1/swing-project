package com.booklink.ui.panel.menu;

import com.booklink.utils.UserHolder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserPanel extends JPanel {
    private JButton loginButton;
    private JButton logoutButton;
    private JButton signUpButton;
    private boolean loggedIn = UserHolder.isLogin();



    public UserPanel() {
        setPreferredSize(new Dimension(400, 50));
        setLayout(null);
        logoutButton = new JButton("Logout");
        loginButton = new JButton("Login");
        signUpButton = new JButton("Sign Up");

        logoutButton.setBounds(0, 0, 100, 50);

        add(logoutButton);
        add(loginButton).setBounds(0, 0, 100, 50);
        add(signUpButton).setBounds(100, 0, 100, 50);




        // 로그인 버튼 누르면 로그인 폼으로 이동
        loginButton.addActionListener(e -> {

            if (!loggedIn) {
                new LoginForm(this).setVisible(true);
            } else {
                // Perform logout action
                loggedIn = false;
                updateButtons();
            }
        });

        logoutButton.addActionListener(e -> {
            UserHolder.logOut(); // 로그아웃
            updateButtons();
        });

        //sign up 버튼 누르면 회원가입 폼으로 이동
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SignForm(null); // Open the SingForm
            }
        });

    }
    public void updateButtons() {
        if (UserHolder.isLogin()) {
            loginButton.setVisible(false);
            logoutButton.setVisible(true);
        } else {
            loginButton.setVisible(true);
            logoutButton.setVisible(false);
        }
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
        updateButtons();
    }

}