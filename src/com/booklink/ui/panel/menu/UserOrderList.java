package com.booklink.ui.panel.menu;


import com.booklink.controller.BookController;
import com.booklink.dao.OrderDao;
import com.booklink.model.book.Book;
import com.booklink.model.order.OrderDto;
import com.booklink.ui.frame.main.MainFrame;
import com.booklink.ui.panel.content.book.bookdetail.BookDetailPanel;
import com.booklink.utils.UserHolder;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Optional;

public class UserOrderList extends JDialog {
    private final MyPagePanel owner;
    private OrderDao orderDao;
    private JLabel lblTitle;
    private JButton btnSelect;
    private JButton btnCancel;
    private JList<String> orderList;
    private DefaultListModel<String> listModel;
    private List<OrderDto> orders; // 주문 목록을 저장하느 필드
    private BookController bookController = new BookController();

    public UserOrderList(MyPagePanel owner) {
        super((Frame) null, "도서 주문 목록", true);
        this.owner = owner;
        this.orderDao = new OrderDao();
        init();
        setDisplay();
        setLocationRelativeTo(owner);
        setVisible(true);


    }

    private void init() {

        // 크기 고정
        int tfSize = 10;
        Dimension lblSize = new Dimension(150, 35);
        Dimension btnSize = new Dimension(100, 25);

        lblTitle = new JLabel("나의  도서 주문 목록");
        lblTitle.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));

        btnSelect = new JButton("선택");
        btnSelect.setPreferredSize(btnSize);
        btnCancel = new JButton("취소");
        btnCancel.setPreferredSize(btnSize);

        listModel = new DefaultListModel<>();
        orderList = new JList<>(listModel);
        orderList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        orderList.setVisibleRowCount(10);
        JScrollPane listScrollPane = new JScrollPane(orderList);

        loadOrders();


        btnSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = orderList.getSelectedIndex();
                if (selectedIndex != -1) {
                    OrderDto selectedOrder = orders.get(selectedIndex);
                    Long bookId = selectedOrder.bookId();
                    if (bookId != null) {
                        // 선택된 도서의 ID를 이용하여 Book 객체를 가져오고 BookDetailPanel로 전환
                        Optional<Book> selectedBook = bookController.findByBookId(bookId);
                        if (selectedBook.isPresent()) {
                            MainFrame mainFrame = (MainFrame) SwingUtilities.getWindowAncestor(owner);
                            mainFrame.changeCurrentContent(new BookDetailPanel(mainFrame, selectedBook.orElse(null)));
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(UserOrderList.this, "도서 정보를 가져올 수 없습니다.");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(UserOrderList.this, "도서를 선택하세요.");
                }
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnSelect);
        buttonPanel.add(btnCancel);

        setLayout(new BorderLayout());
        add(lblTitle, BorderLayout.NORTH);
        add(listScrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setSize(600, 500);
    }

    private void loadOrders() {
        Long userID = UserHolder.getId();
        orders = orderDao.findAllOrderByUserId(userID);


        if (orders.isEmpty()) {
            listModel.addElement("구매한 도서가 없습니다.");
        } else {
            for (OrderDto order : orders) {
                String orderInfo = String.format("책 제목: %s, 가격: %d, 주문 날짜: %s",
                        order.bookTitle(), order.price(), order.purchasedDate().toString(), order.bookImageUrl());
                listModel.addElement(orderInfo);
            }
        }
    }

    private void setDisplay() {
        FlowLayout flowLeft = new FlowLayout(FlowLayout.LEFT);
    }
}
