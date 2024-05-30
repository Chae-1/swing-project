package com.booklink.ui.panel.menu;

import com.booklink.controller.CategoryController;
import com.booklink.service.UserService;
import com.booklink.ui.frame.main.MainFrame;
import com.booklink.ui.panel.content.ContentPanel;
import com.booklink.ui.panel.content.book.BookContentPanel;
import com.booklink.utils.UserHolder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.List;
import java.util.stream.IntStream;

public class MyPagePanel extends ContentPanel {
    private String username;
    private MainFrame mainFrame;
    private Set<String> selectedCategories;
    private CategoryController controller;
    private List<String> categoryNames;

    public MyPagePanel(MainFrame mainFrame) {
        super(mainFrame);
        controller = new CategoryController();
        categoryNames = controller.findAll();
        this.username = UserHolder.getName();
        this.mainFrame = mainFrame;
        this.selectedCategories = new HashSet<>();
        init();
        setDisplay();
        mainFrame.changeCurrentContent(this);
    }

    protected void init() {
        super.init();
        setLayout(new BorderLayout());
    }

    private void setDisplay() {
        // 상단 사용자 이름 제목
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel userLabel = new JLabel("사용자 이름 : " + username);
        userLabel.setFont(new Font("", Font.BOLD, 20));
        JLabel titleLabel = new JLabel("나는 이런 도서 종류에 관심이 많아요.", SwingConstants.CENTER);
        titleLabel.setFont(new Font("", Font.BOLD, 30));
        topPanel.add(userLabel, BorderLayout.WEST);
        topPanel.add(titleLabel, BorderLayout.CENTER);

        // 왼쪽 버튼 패널
        JPanel leftPanel = new JPanel(new GridLayout(3, 1));

        JButton btnPurchaseHistory = new JButton("주문 목록");
        JButton btnEditInfo = new JButton("회원정보 수정");
        JButton btnDeleteAccount = new JButton("회원 탈퇴");

        leftPanel.add(btnPurchaseHistory);
        leftPanel.add(btnEditInfo);
        leftPanel.add(btnDeleteAccount);

        // 중앙 컨텐츠 패널
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(4, 2, 10, 10)); // 2열 4행, 간격 10픽셀

        String[] categories = new Random().ints(0, categoryNames.size())
                .distinct()
                .limit(8)
                .mapToObj(num -> categoryNames.get(num))
                .toArray(String[]::new);

        for (String category : categories) {
            JToggleButton btn = new JToggleButton(category);
            btn.setPreferredSize(new Dimension(40, 20));
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (btn.isSelected()) {
                        selectedCategories.add(category);
                    } else {
                        selectedCategories.remove(category);
                    }
                }
            });
            centerPanel.add(btn);
        }

        // 하단 패널
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton selectButton = new JButton("선택");
        JButton cancelButton = new JButton("취소");

        // 선택 버튼 기능 추가
        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // selectedCategories
                mainFrame.changeCurrentContent(new BookContentPanel(mainFrame, selectedCategories));
            }
        });

        // 취소 버튼 기능 추가
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedCategories.clear();
                for (Component comp : centerPanel.getComponents()) {
                    if (comp instanceof JToggleButton) {
                        ((JToggleButton) comp).setSelected(false);
                    }
                }
            }
        });

        bottomPanel.add(selectButton);
        bottomPanel.add(cancelButton);

        add(topPanel, BorderLayout.NORTH);
        add(leftPanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // 주문 목록 페이지 오픈
        btnPurchaseHistory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UserOrderList(MyPagePanel.this);
            }
        });

        // 회원 수정 페이지 오픈
        btnEditInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UserProfileEditDialog(MyPagePanel.this);
            }
        });

        // 회원 탈퇴 버튼 기능 추가
        btnDeleteAccount.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "정말로 회원 탈퇴를 하시겠습니까?", "회원 탈퇴", JOptionPane.YES_NO_OPTION);
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
    public void updateDisplay(int page) {
        // 예제에서는 페이지 업데이트가 필요하지 않음
    }
}