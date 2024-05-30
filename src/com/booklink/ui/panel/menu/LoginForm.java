package com.booklink.ui.panel.menu;

import com.booklink.dao.UserDao;
import com.booklink.model.user.User;
import com.booklink.utils.UserHolder;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

public class LoginForm extends JFrame {
    private UserPanel userPanel;
    private UserDataSet users;

    private JLabel lblLogTitle;
    private JLabel lblId;
    private JLabel lblPw;
    private JTextField tfId;
    private JPasswordField tfPw;
    private JButton btnLogin;
    private JButton btnsign;


    public LoginForm(UserPanel userPanel) {
        this.userPanel = userPanel;
        users = new UserDataSet();

        init();
        setDisplay();
        addListeners();
        showFrame();
    }

    public void init() {
        // 사이즈 통합
        Dimension lblSize = new Dimension(80, 30);
        int tfSize = 20;
        Dimension btnSize = new Dimension(100, 25);

        lblLogTitle = new JLabel("BookLink 로그인");
        lblLogTitle.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        lblId = new JLabel("ID");
        lblId.setPreferredSize(lblSize);
        lblPw = new JLabel("Password");
        lblPw.setPreferredSize(lblSize);

        tfId = new JTextField(tfSize);
        tfPw = new JPasswordField(tfSize);

        btnLogin = new JButton("로그인");
        btnLogin.setPreferredSize(btnSize);
        btnsign = new JButton("회원가입");
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
        FlowLayout flowCenter = new FlowLayout(FlowLayout.CENTER);

        // pnlNorth(pnlId, pnlPw)
        JPanel pnlNorth = new JPanel(new GridLayout(0, 1));


        JPanel pnlId = new JPanel(flowCenter);
        pnlId.add(lblId);
        pnlId.add(tfId);

        JPanel pnlPw = new JPanel(flowCenter);
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
        setSize(500, 200); // 로그인 폼 크기 설정
        setLocationRelativeTo(null);
        setDefaultCloseOperation(HIDE_ON_CLOSE); // 로그인 창을 닫을 때 프로그램 종료 설정
        setResizable(false);
        setVisible(true);
    }

    public void login(String username, String password) {
        UserDao userDao = new UserDao();
        // -> Controller
        // 1. 파라미터에 대한 유효성 검증 -> Controller
        // 2. 해당 파라미터로 조회를 했을 때, 중복 건 수가 없는지 확인 -> Service
        // 3. 저장, 수정, 조회 -> Dao, Repository

        // UserDao를 통해 사용자 인증을 시도함.
        Optional<User> authenticatedUser = userDao.findByLogIdAndPassword(username, password);

        if (authenticatedUser.isPresent()) {
            // 로그인 성공 처리
            UserHolder.logIn(authenticatedUser.get());

            JOptionPane.showMessageDialog(LoginForm.this,
                    "로그인 성공!",
                    "로그인 성공",
                    JOptionPane.INFORMATION_MESSAGE);
            setVisible(false);
            // 추가 작업 수행 가능

            userPanel.setLoggedIn(true);
        } else {
            // 로그인 실패 처리
            JOptionPane.showMessageDialog(LoginForm.this,
                    "유효하지 않은 사용자 이름 또는 비밀번호입니다.",
                    "로그인 실패",
                    JOptionPane.ERROR_MESSAGE);
        }
    }


}
