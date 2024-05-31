package com.booklink.ui.panel.menu;

import com.booklink.model.user.User;
import com.booklink.ui.frame.main.MainFrame;
import com.booklink.ui.panel.content.book.BookContentPanel;
import com.booklink.utils.UserHolder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserPanel extends JPanel {
    private JButton loginButton;
    private JButton logoutButton;
    private JButton signUpButton;
    private JButton mypageButton;
    private boolean loggedIn = UserHolder.isLogin();



    public UserPanel() {
        setPreferredSize(new Dimension(400, 50));
        setLayout(null);
        logoutButton = new JButton("로그아웃");
        loginButton = new JButton("로그인");
        signUpButton = new JButton("회원가입");
        mypageButton = new JButton("마이페이지");


        logoutButton.setBounds(100, 0, 100, 50);
        mypageButton.setBounds(200,0,100,50);

        add(logoutButton);
        add(mypageButton);
        add(loginButton).setBounds(100, 0, 100, 50);
        add(signUpButton).setBounds(200, 0, 100, 50);


        // 로그인 버튼 누르면 로그인 폼으로 이동
        loginButton.addActionListener(e -> {
            if (!UserHolder.isLogin()) {
                new LoginForm(this).setVisible(true);
            } else {
                // Perform logout action
                UserHolder.logOut();
                updateButtons();
            }
        });

        logoutButton.addActionListener(e -> {
            UserHolder.logOut(); // 로그아웃
            updateButtons();
            MainFrame mainFrame = (MainFrame) SwingUtilities.getWindowAncestor(this);
            mainFrame.clearPrevPage(new BookContentPanel(mainFrame));
            // 로그아웃 메세지 다이얼로그
            JOptionPane optionPane = new JOptionPane(
                    "로그아웃 되었습니다.",
                    JOptionPane.INFORMATION_MESSAGE
            );
            JDialog dialog = optionPane.createDialog(mainFrame, "로그아웃");
            dialog.setLocationRelativeTo(mainFrame); /// 다이얼로그 화면 중앙에 표시.
            dialog.setVisible(true);
            mainFrame.changeCurrentContent(new BookContentPanel(mainFrame)); // 초기 화면으로 돌아가기
        });

        // 회원가입 버튼 누르면 회원가입 폼으로 이동
        signUpButton.addActionListener(e -> new SignForm(null));


        // 마이페이지 버튼 누르면 마이페이지 폼으로 이동
        mypageButton.addActionListener(e -> {
            if (UserHolder.isLogin()) {
                MainFrame mainFrame = (MainFrame) SwingUtilities.getWindowAncestor(this);
                new MyPagePanel(mainFrame);
            }
        });
        updateButtons();

    }
    public void updateButtons() {
        if (UserHolder.isLogin()) {
            loginButton.setVisible(false);
            logoutButton.setVisible(true);
            mypageButton.setVisible(true);
        } else {
            loginButton.setVisible(true);
            logoutButton.setVisible(false);
            mypageButton.setVisible(false);

        }
    }


    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
        updateButtons();
    }

}