package com.booklink.ui.panel.content.book.bookdetail;

import com.booklink.controller.BookController;
import com.booklink.controller.CategoryController;
import com.booklink.controller.OrderController;
import com.booklink.model.book.Book;
import com.booklink.model.book.exception.BookNotExistException;
import com.booklink.model.user.exception.UserException;
import com.booklink.model.user.exception.UserNotFoundException;
import com.booklink.ui.frame.main.MainFrame;
import com.booklink.ui.panel.content.ContentPanel;

import com.booklink.ui.panel.content.book.BookContentPanel;
import com.booklink.ui.panel.content.book.bookdetail.comment.CommentPanel;
import com.booklink.ui.panel.content.book.bookdiscussion.BookDiscussionPanel;
import com.booklink.ui.panel.content.book.bookregister.BookRegisterDialog;
import com.booklink.utils.UserHolder;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class BookDetailPanel extends ContentPanel {

    private CategoryController controller = new CategoryController();
    private BookController bookController = new BookController();
    private OrderController orderController = new OrderController();

    public BookDetailPanel(MainFrame mainFrame, Book book) {
        super(mainFrame);
        init();
        JLabel titleLabel = new JLabel(book.getTitle() + " : " + book.getAuthor() + "/" + book.getPublisher());
        titleLabel.setSize(815, 100);
        titleLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel).setBounds(0, 0, 715, 100);

        JButton discBookButton = new JButton("의견공유");
        discBookButton.setBounds(715, 0, 100, 100);
        discBookButton.addActionListener((e) -> {
            try {
                mainFrame.changeCurrentContent(new BookDiscussionPanel(mainFrame, book));
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, ex.getMessage(), "경고", JOptionPane.WARNING_MESSAGE);
                return;
            }
        });
        add(discBookButton);

        JButton removeBookButton = new JButton("삭제");
        removeBookButton.setBounds(815, 0, 100, 100);
        removeBookButton.addActionListener((e) -> {
            try {
                bookController.removeBookById(book.getId());
                // 삭제 이후
                JOptionPane optionPane = new JOptionPane(
                        "삭제 되었습니다.",
                        JOptionPane.INFORMATION_MESSAGE
                );
                JDialog dialog = optionPane.createDialog(mainFrame, "삭제");
                dialog.setLocationRelativeTo(mainFrame); /// 다이얼로그 화면 중앙에 표시.
                dialog.setVisible(true);

                mainFrame.changeCurrentContent(new BookContentPanel(mainFrame)); // 초기 화면으로 돌아가기

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, ex.getMessage(), "경고", JOptionPane.WARNING_MESSAGE);
                return;
            }
        });
        add(removeBookButton);

        JButton updateBookButton = new JButton("수정");
        updateBookButton.addActionListener((e) -> {
            if (UserHolder.isRoot()) {
                BookRegisterDialog dialog = new BookRegisterDialog(mainFrame, book);
                dialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this,
                        "접근 권한이 없습니다.",
                        "경고", JOptionPane.WARNING_MESSAGE);

            }
        });
        updateBookButton.setBounds(915, 0, 100, 100);
        add(updateBookButton);

        // 이미지 490 * 200, 위치 :
        URL location = null;
        try {
            String imageUrl = book.getImageUrl();
            System.out.println(imageUrl);
            location = new URL(imageUrl);
        } catch (MalformedURLException e) {
            try {
                location = new URL("https://th.bing.com/th/id/OIP.94MjdNLLzgxs1l_L0zNLGwHaHa?w=186&h=186&c=7&r=0&o=5&dpr=1.5&pid=1.7");
            } catch (MalformedURLException ex) {
                throw new RuntimeException(ex);
            }
        }

        ImageIcon image = new ImageIcon(location);
        resizeImage(image);
        if (image.getImageLoadStatus() == MediaTracker.COMPLETE) {
            System.out.println("이미지가 성공적으로 로드되었습니다.");
        } else {
            System.out.println("이미지 로드에 실패했습니다.");
        }

        JLabel imageLabel = new JLabel(image);
        imageLabel.setBounds(0, 100, 490, 300);
        imageLabel.setBackground(Color.YELLOW);
        add(imageLabel);

        JLabel priceLabel = new JLabel(String.valueOf(book.getPrice()));
        Font malgunGothic = new Font("Malgun Gothic", Font.BOLD, 20);
        priceLabel.setFont(malgunGothic);
        priceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        priceLabel.setBounds(490, 100, 715, 50);
        add(priceLabel);

        JLabel summaryLabel = new JLabel(book.getSummary());
        summaryLabel.setFont(malgunGothic);
        summaryLabel.setHorizontalAlignment(SwingConstants.CENTER);
        summaryLabel.setBounds(490, 150, 715, 50);
        add(summaryLabel);

        String description = "<html>" + insertLineBreaks(book.getDescription(), 30) + "</html>";
        JLabel descriptionLabel = new JLabel(description);
        descriptionLabel.setFont(malgunGothic);
        descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        descriptionLabel.setBounds(490, 200, 715, 200);
        add(descriptionLabel);

        JLabel scoreLabel = new JLabel(book.getRating() + " / " + " 5");
        scoreLabel.setFont(malgunGothic);
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scoreLabel.setBounds(0, 400, 490, 100);
        add(scoreLabel);

        // 카테고리 추가
        System.out.println(book.getId());
        List<String> allCategories = controller.findAllCategories(book.getId());
        System.out.println(allCategories);
        String categoryList = allCategories.stream()
                .reduce((category1, category2) -> category1 + "<br>" + category2)
                .orElse("분류 없음");
        categoryList = "<html>" + categoryList + "</html>";
        System.out.println(categoryList);
        JLabel categoriesLabel = new JLabel(categoryList);
        categoriesLabel.setFont(malgunGothic);
        categoriesLabel.setBounds(490, 400, 515, 100);
        add(categoriesLabel);

        JButton purchaseButton = new JButton("구매");
        purchaseButton.addActionListener((e) -> {
            try {
                if (UserHolder.isLogin()) {
                    // ValueObj
                    orderController.createOrder(book.getId(), UserHolder.getId());
                    JOptionPane.showMessageDialog(this, "주문을 성공적으로 완료했습니다.",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "로그인을 우선적으로 해주세요.",
                            "Fail", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, ex.getMessage(),
                        "Fail", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        purchaseButton.setFont(malgunGothic);
        purchaseButton.setBounds(1005, 400, 100, 100);
        add(purchaseButton);

        CommentPanel commentPanel = new CommentPanel(mainFrame, book.getId());
        commentPanel.setBounds(0, 500, 1215, 400);
        add(commentPanel);
    }

    public static String insertLineBreaks(String text, int maxLength) {
        StringBuilder sb = new StringBuilder();
        int index = 0;
        while (index < text.length()) {
            // 현재 위치부터 maxLength까지의 부분 문자열을 추출
            int end = Math.min(index + maxLength, text.length());
            sb.append(text, index, end);
            // 문자열 끝이 아니면 <br> 태그 삽입
            if (end < text.length()) {
                sb.append("<br>");
            }
            index += maxLength;
        }
        return sb.toString();
    }

    private void resizeImage(ImageIcon image) {
        Image originalImage = image.getImage();

        // 이미지 크기 조정 (JLabel의 크기에 맞추기)
        int labelWidth = 490;
        int labelHeight = 300;
        Image resizedImage = originalImage.getScaledInstance(labelWidth, labelHeight, Image.SCALE_SMOOTH);
        image.setImage(resizedImage);
    }
    //
    // 관련있는 댓글 목록을 전부 가지고 와야 한다.
    // 관련있는 카테고리도 전부 가지고 와야 한다.

    @Override
    public int getMaxPage() {
        return 0;
    }

    @Override
    protected int getCurrentPage() {
        return 0;
    }

    @Override
    public void updateDisplay(int page) {

    }

    @Override
    protected void init() {
        setLayout(null);
        setSize(contentWidth, contentHeight);
    }
}