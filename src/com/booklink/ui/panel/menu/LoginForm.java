package com.booklink.ui.panel.menu;

import com.booklink.dao.UserDao;
import com.booklink.model.user.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

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


            btnLogin.addActionListener(new ActionListener() { //로그인 여부 판단 확인
                @Override
                public void actionPerformed(ActionEvent e) {
                    String username = tfId.getText();
                    String password = String.valueOf(tfPw.getPassword());

                    login(username, password);

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

        public void login(String username, String password) {
            UserDao userDao = new UserDao();

            // UserDao를 통해 사용자 인증을 시도함.
            Optional<User> authenticatedUser = userDao.findByLogIdAndPassword(username, password);

            if (authenticatedUser.isPresent()) {
                // 로그인 성공 처리
                JOptionPane.showMessageDialog(LoginForm.this,
                        "로그인 성공!",
                        "로그인 성공",
                        JOptionPane.INFORMATION_MESSAGE);
                setVisible(false);

                // 추가 작업 수행 가능
            } else {
                // 로그인 실패 처리
                JOptionPane.showMessageDialog(LoginForm.this,
                        "유효하지 않은 사용자 이름 또는 비밀번호입니다.",
                        "로그인 실패",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        public static void main(String[] args) {
            new LoginForm();
        }

    }