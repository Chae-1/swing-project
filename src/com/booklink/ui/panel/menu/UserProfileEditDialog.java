package com.booklink.ui.panel.menu;

import com.booklink.dao.UserDao;
import com.booklink.utils.UserHolder;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserProfileEditDialog extends JDialog {
    private final MyPagePanel owner;
    private UserDao userDao;
    private JLabel lblTitle;
    private JLabel lblPw;
    private JLabel lblRe;
    private JPasswordField tfPw;
    private JPasswordField tfRe;
    private JButton btnEdit;
    private JButton btnCancel;

    public UserProfileEditDialog(MyPagePanel owner) {
        super((Frame) null, "회원 정보 수정", true);  // Set modal and with title
        this.owner = owner;
        this.userDao = new UserDao();
        init();
        setDisplay();
        pack();  // Automatically size the dialog to fit its contents
        setLocationRelativeTo(owner);  // Center the dialog relative to its owner
        setVisible(true);  // Make the dialog visible
    }

    private void init() {
        int tfSize = 10;
        Dimension lblSize = new Dimension(150, 35);
        Dimension btnSize = new Dimension(100, 25);

        lblTitle = new JLabel("비밀번호 수정");
        lblTitle.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        lblPw = new JLabel("새 비밀번호", JLabel.LEFT);
        lblPw.setPreferredSize(lblSize);
        lblRe = new JLabel("비밀번호 확인", JLabel.LEFT);
        lblRe.setPreferredSize(lblSize);

        tfPw = new JPasswordField(tfSize);
        tfRe = new JPasswordField(tfSize);

        btnEdit = new JButton("수정");
        btnEdit.setPreferredSize(btnSize);
        btnCancel = new JButton("취소");
        btnCancel.setPreferredSize(btnSize);

        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleEditAction();
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void handleEditAction() {
        String password = new String(tfPw.getPassword());
        String rePassword = new String(tfRe.getPassword());

        if (!password.equals(rePassword)) {
            JOptionPane.showMessageDialog(this, "비밀번호가 일치하지 않습니다.", "오류", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 비밀번호 업데이트
        long userId = UserHolder.getId();
        userDao.updatePassword(userId, password);

        JOptionPane.showMessageDialog(this, "비밀번호가 성공적으로 수정되었습니다.", "성공", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }

    private void setDisplay() {
        FlowLayout flowLeft = new FlowLayout(FlowLayout.LEFT);

        JPanel pnlMain = new JPanel(new BorderLayout());

        JPanel pnlMNorth = new JPanel(flowLeft);
        pnlMNorth.add(lblTitle);

        JPanel pnlMCenter = new JPanel(new GridLayout(0, 1));
        JPanel pnlPw = new JPanel(flowLeft);
        pnlPw.add(lblPw);
        pnlPw.add(tfPw);

        JPanel pnlRe = new JPanel(flowLeft);
        pnlRe.add(lblRe);
        pnlRe.add(tfRe);

        pnlMCenter.add(pnlPw);
        pnlMCenter.add(pnlRe);

        JPanel pnlMSouth = new JPanel(new FlowLayout(FlowLayout.CENTER));

        pnlMain.add(pnlMNorth, BorderLayout.NORTH);
        pnlMain.add(pnlMCenter, BorderLayout.CENTER);
        pnlMain.add(pnlMSouth, BorderLayout.SOUTH);

        JPanel pnlSouth = new JPanel();
        pnlSouth.add(btnEdit);
        pnlSouth.add(btnCancel);

        pnlMain.setBorder(new EmptyBorder(50, 20, 20, 20));
        pnlSouth.setBorder(new EmptyBorder(0, 0, 20, 0));

        getContentPane().add(pnlMain, BorderLayout.CENTER);
        getContentPane().add(pnlSouth, BorderLayout.SOUTH);
    }
}