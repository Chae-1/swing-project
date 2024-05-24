package com.booklink.ui.panel.menu;

import com.booklink.dao.UserDao;
import com.booklink.model.user.UserRegistrationDto;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;

public class SignForm extends JDialog {
    private LoginForm owner;
    private UserDataSet users;


    private JLabel lblTitle;
    private JLabel lblId;
    private JLabel lblPw;
    private JLabel lblRe;
    private JLabel lblName;
    private JTextField tfId;
    private JPasswordField tfPw;
    private JPasswordField tfRe;
    private JTextField tfName;
    private JButton btnSign;
    private JButton btnCancel;

    public SignForm(LoginForm owner) {
        super(owner, "Sign up", true);
        this.owner = owner;

        if (owner != null) {
            users = owner.getUsers();
        } else {
            users = new UserDataSet();

        }

        init();
        setDisplay();
        addListeners();
        showFrame();
    }

    private void init() {
        // 크기 고정
        int tfSize = 10;
        Dimension lblSize = new Dimension(150, 35);
        Dimension btnSize = new Dimension(100, 25);


        lblTitle = new JLabel("회원가입");
        lblTitle.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        lblId = new JLabel("ID", JLabel.LEFT);
        lblId.setPreferredSize(lblSize);
        lblPw = new JLabel("Password", JLabel.LEFT);
        lblPw.setPreferredSize(lblSize);
        lblRe = new JLabel("Retype Your Password", JLabel.LEFT);
        lblRe.setPreferredSize(lblSize);
        lblName = new JLabel("Name", JLabel.LEFT);
        lblName.setPreferredSize(lblSize);

        tfId = new JTextField(tfSize);
        tfPw = new JPasswordField(tfSize);
        tfRe = new JPasswordField(tfSize);
        tfName = new JTextField(tfSize);

        btnSign = new JButton("Sign up");
        btnSign.setPreferredSize(btnSize);
        btnCancel = new JButton("Cancel");
        btnCancel.setPreferredSize(btnSize);

    }

    private void setDisplay() {
        // FlowLayout 왼쪽 정렬
        FlowLayout flowLeft = new FlowLayout(FlowLayout.LEFT);

        // pnlMain(pnlMNorth / pnlMCenter / pnlMSouth)
        JPanel pnlMain = new JPanel(new BorderLayout());

        // pnlMNorth(lblTitle)
        JPanel pnlMNorth = new JPanel(flowLeft);
        pnlMNorth.add(lblTitle);

        // pnlMCenter(pnlId / pnlPw / pnlRe / pnlName)
        JPanel pnlMCenter = new JPanel(new GridLayout(0, 1));
        JPanel pnlId = new JPanel(flowLeft);
        pnlId.add(lblId);
        pnlId.add(tfId);

        JPanel pnlPw = new JPanel(flowLeft);
        pnlPw.add(lblPw);
        pnlPw.add(tfPw);

        JPanel pnlRe = new JPanel(flowLeft);
        pnlRe.add(lblRe);
        pnlRe.add(tfRe);

        JPanel pnlName = new JPanel(flowLeft);
        pnlName.add(lblName);
        pnlName.add(tfName);

        pnlMCenter.add(pnlId);
        pnlMCenter.add(pnlPw);
        pnlMCenter.add(pnlRe);
        pnlMCenter.add(pnlName);

        // pnlMSouth
        JPanel pnlMSouth = new JPanel(new FlowLayout(FlowLayout.CENTER));

        pnlMain.add(pnlMNorth, BorderLayout.NORTH);
        pnlMain.add(pnlMCenter, BorderLayout.CENTER);
        pnlMain.add(pnlMSouth, BorderLayout.SOUTH);

        // pnlSouth(btnJoin / btnCancel)
        JPanel pnlSouth = new JPanel();
        pnlSouth.add(btnSign);
        pnlSouth.add(btnCancel);

        pnlMain.setBorder(new EmptyBorder(50, 20, 20, 20));
        pnlSouth.setBorder(new EmptyBorder(0, 0, 20, 0));

        add(pnlMain, BorderLayout.CENTER);
        add(pnlSouth, BorderLayout.SOUTH);
    }

    private void addListeners() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                dispose();
                owner.setVisible(true);
            }
        });
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                dispose();
                owner.setVisible(true);
            }
        });
        btnSign.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                // 정보 하나라도 비어있으면
                if (isBlank()) {
                    JOptionPane.showMessageDialog(
                            SignForm.this,
                            "모든 정보를 입력해주세요."
                    );
                    // 모두 입력했을 때
                } else {
                    // Id 중복일 때
                    if (users.isIdOverlap(tfId.getText())) {
                        JOptionPane.showMessageDialog(SignForm.this,
                                "이미 존재하는 Id입니다.");
                        tfId.requestFocus();

                        // Pw와 Re가 일치하지 않았을 때
                    } else if (!String.valueOf(tfPw.getPassword()).equals(String.valueOf(tfRe.getPassword()))) {
                        JOptionPane.showMessageDialog(SignForm.this,
                                "Password와 Retry가 일치하지 않습니다.");
                        tfPw.requestFocus();
                    } else {
                        // UserRegistrationDto 객체 생성
                        UserRegistrationDto userDto = new UserRegistrationDto(
                                tfName.getText(),
                                String.valueOf(tfPw.getPassword()),
                                tfId.getText(),
                                LocalDateTime.now(),
                                null // 이미지 경로 (이미지 업로드 기능 추가 시 수정)
                        );
                        UserDao userDao = new UserDao();
                        userDao.registerUser(userDto);


                        JOptionPane.showMessageDialog(SignForm.this, "회원가입을 완료했습니다!");
                        dispose();
                        owner.setVisible(true);
                    }
                }
            }
        });
    }

    public boolean isBlank() {
        boolean result = false;
        if (tfId.getText().isEmpty()) {
            tfId.requestFocus();
            return true;
        }
        if (String.valueOf(tfPw.getPassword()).isEmpty()) {
            tfPw.requestFocus();
            return true;
        }
        if (String.valueOf(tfRe.getPassword()).isEmpty()) {
            tfRe.requestFocus();
            return true;
        }
        if (tfName.getText().isEmpty()) {
            tfName.requestFocus();
            return true;
        }
        return result;
    }

    private void showFrame() {
        pack();
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }
}