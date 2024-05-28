package com.booklink.ui.panel.menu;

import com.booklink.service.UserService;
import com.booklink.ui.frame.main.MainFrame;
import com.booklink.ui.panel.content.ContentPanel;
import com.booklink.ui.panel.content.book.BookContentPanel;
import com.booklink.utils.UserHolder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyPagePanel extends ContentPanel {
    private String username;
    private MainFrame mainFrame;

    public MyPagePanel(MainFrame mainFrame) {
        super(mainFrame);
        this.username = username;
        this.mainFrame = mainFrame;
        init();
        setDisplay();
        mainFrame.changeCurrentContent(this);
    }

    protected void init() {
        super.init();
        setLayout(new BorderLayout());
    }

    private void setDisplay() {
        // 상단 사용자 이름
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel userLabel = new JLabel("user name: " + username);
        topPanel.add(userLabel);

        // 왼쪽 버튼 패널
        JPanel leftPanel = new JPanel(new GridLayout(4, 1));
        JButton btnCategory = new JButton("선호 카테고리");
        JButton btnPurchaseHistory = new JButton("주문 목록");
        JButton btnEditInfo = new JButton("회원정보 수정");
        JButton btnDeleteAccount = new JButton("회원 탈퇴");

        leftPanel.add(btnCategory);
        leftPanel.add(btnPurchaseHistory);
        leftPanel.add(btnEditInfo);
        leftPanel.add(btnDeleteAccount);

        // 중앙 컨텐츠 패널
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        JLabel centerLabel = new JLabel("나는 ___ 도서 종류에 관심이 많아요");
        centerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(centerLabel);

        // Example buttons in the center panel
        for (int i = 0; i < 6; i++) {
            JButton btn = new JButton("category " + (i + 1));
            btn.setPreferredSize(new Dimension(150, 50));
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            centerPanel.add(btn);
            centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        add(topPanel, BorderLayout.NORTH);
        add(leftPanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);



        //주문 목록 페이지 오픈
        btnPurchaseHistory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UserOrderList(MyPagePanel.this); //
            }
        });



        // 회원 수정 패이지 오픈
        btnEditInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UserProfileEditDialog(MyPagePanel.this);
            }
        });

        // 회원 탈퇴 버튼 기능 추가
        btnDeleteAccount.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    " 정말로 회원 탈퇴를 하시겠습니까?", "회원 탈퇴", JOptionPane.YES_NO_OPTION );
            if (confirm == JOptionPane.YES_OPTION) {
                UserService userService = new UserService();
                userService.deleteUserById(UserHolder.getId());
                UserHolder.logOut();
                mainFrame.changeCurrentContent(new BookContentPanel(mainFrame));
                JOptionPane.showMessageDialog(this,
                        "회원 탈퇴가 완료되었습니다.", "탈퇴 완료", JOptionPane.INFORMATION_MESSAGE);
            }

        });
    }


    @Override
    protected int getMaxPage() {
        return 1; // 예제에서는 페이지 개념이 필요 없으므로 1로 설정
    }

    @Override
    protected int getCurrentPage() {
        return 1; // 예제에서는 페이지 개념이 필요 없으므로 1로 설정
    }

    @Override
    protected void update(int page) {
        // 예제에서는 페이지 업데이트가 필요하지 않음
    }
}