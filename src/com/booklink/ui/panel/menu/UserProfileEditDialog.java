package com.booklink.ui.panel.menu;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class UserProfileEditDialog {
    private final MyPagePanel owner;
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

    public UserProfileEditDialog(MyPagePanel owner) {
        super();
        this.owner = owner;
        init();
        setDisplay();

    }


    private void init() {
        // 크기 고정
        int tfSize = 10;
        Dimension lblSize = new Dimension(150, 35);
        Dimension btnSize = new Dimension(100, 25);


        lblTitle = new JLabel("회원 수정");
        lblTitle.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        lblId = new JLabel("ID", JLabel.LEFT);
        lblId.setPreferredSize(lblSize);
        lblPw = new JLabel("Password", JLabel.LEFT);
        lblPw.setPreferredSize(lblSize);
        lblRe = new JLabel("Retype Edit Your Password", JLabel.LEFT);
        lblRe.setPreferredSize(lblSize);
        lblName = new JLabel("Edit Name", JLabel.LEFT);
        lblName.setPreferredSize(lblSize);

        tfId = new JTextField(tfSize);
        tfPw = new JPasswordField(tfSize);
        tfRe = new JPasswordField(tfSize);
        tfName = new JTextField(tfSize);

        btnSign = new JButton("Edit");
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
        
    }


}
