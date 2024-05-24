package com.booklink.ui.panel.menu;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginForm extends JFrame {

    private UserDataSet users;

    private JLabel lblLogTitle;
    private JLabel lblId;
    private JLabel lblPw;
    private JTextField tfId;
    private JPasswordField tfPw;
    private JButton btnLogin;
    private JButton btnsign;


    public LoginForm() {

        users = new UserDataSet();

        init();
        setDisplay();
        addListeners();
        showFrame();
    }

    public void init() {
        // 사이즈 통합
        Dimension lblSize = new Dimension(80, 30);
        int tfSize = 10;
        Dimension btnSize = new Dimension(100, 25);

        lblLogTitle = new JLabel("로그인");
        lblLogTitle.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        lblId = new JLabel("ID");
        lblId.setPreferredSize(lblSize);
        lblPw = new JLabel("Password");
        lblPw.setPreferredSize(lblSize);

        tfId = new JTextField(tfSize);
        tfPw = new JPasswordField(tfSize);

        btnLogin = new JButton("Login");
        btnLogin.setPreferredSize(btnSize);
        btnsign = new JButton("Sign up");
        btnsign.setPreferredSize(btnSize);

    }
    public UserDataSet getUsers() {
        return users;
    }

    public String getTfId() {
        return tfId.getText();
    }

    public void setDisplay() {

        // FlowLayout 왼쪽 정렬
        FlowLayout flowLeft = new FlowLayout(FlowLayout.LEFT);

        // pnlNorth(pnlId, pnlPw)
        JPanel pnlNorth = new JPanel(new GridLayout(0, 1));


        JPanel pnlId = new JPanel(flowLeft);
        pnlId.add(lblId);
        pnlId.add(tfId);

        JPanel pnlPw = new JPanel(flowLeft);
        pnlPw.add(lblPw);
        pnlPw.add(tfPw);

        pnlNorth.add(lblLogTitle);
        pnlNorth.add(pnlId);
        pnlNorth.add(pnlPw);

        JPanel pnlSouth = new JPanel();
        pnlSouth.add(btnLogin);
        pnlSouth.add(btnsign);

        pnlNorth.setBorder(new EmptyBorder(0, 20, 0, 20));
        pnlSouth.setBorder(new EmptyBorder(0, 0, 10, 0));



        add(pnlNorth, BorderLayout.NORTH);
        add(pnlSouth, BorderLayout.SOUTH);

    }

    public void addListeners() {

        btnsign.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new SignForm(LoginForm.this);
                tfId.setText("");
                tfPw.setText("");
            }
        });
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 아이디칸이 비었을 경우
                if (tfId.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(LoginForm.this,
                            "아이디를 입력하세요.",
                            "로그인폼",
                            JOptionPane.WARNING_MESSAGE);

                    // 존재하는 아이디일 경우
                } else if (users.contains(new UserLoginForm(tfId.getText()))) {

                    // 비밀번호칸이 비었을 경우
                    if(String.valueOf(tfPw.getPassword()).isEmpty()) {
                        JOptionPane.showMessageDialog(
                                LoginForm.this,
                                "비밀번호를 입력하세요.",
                                "로그인폼",
                                JOptionPane.WARNING_MESSAGE);

                        // 비밀번호가 일치하지 않을 경우
                    } else if (!users.getUser(tfId.getText()).getPw().equals(String.valueOf(tfPw.getPassword()))) {
                        JOptionPane.showMessageDialog(
                                LoginForm.this,
                                "비밀번호가 일치하지 않습니다.");

                        // 다 완료될 경우
                    } else {
                        InformationForm infoForm = new InformationForm(LoginForm.this);
                        infoForm.setTaCheck(users.getUser(tfId.getText()).toString());
                        setVisible(false);
                        infoForm.setVisible(true);
                        tfId.setText("");
                        tfPw.setText("");
                    }
                    // 존재하지 않는 Id일 경우
                } else {
                    JOptionPane.showMessageDialog(
                            LoginForm.this,
                            "존재하지 않는 Id입니다."

                    );

                }
            }
        });
    }

    public void showFrame() {
        setTitle("Login");
        setSize(300, 600); // 로그인 폼 크기 설정
        setLocationRelativeTo(null);
        setDefaultCloseOperation(HIDE_ON_CLOSE); // 로그인 창을 닫을 때 프로그램 종료 설정
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        new LoginForm();
    }

}