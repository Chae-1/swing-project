package com.booklink.ui.panel.content.book.bookdetail;

import com.booklink.controller.BookController;
import com.booklink.controller.CategoryController;
import com.booklink.model.book.Book;
import com.booklink.ui.frame.main.MainFrame;
import com.booklink.ui.panel.content.ContentPanel;

import com.booklink.ui.panel.content.book.bookdetail.comment.CommentPanel;
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

    public BookDetailPanel(MainFrame mainFrame, Book book) {
        super(mainFrame);
        init();
        JLabel titleLabel = new JLabel(book.getTitle() + ": " + book.getAuthor() + "/" + book.getPublisher());
        titleLabel.setSize(815, 100);
        titleLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel).setBounds(0, 0, 815, 100);

        JButton removeBookButton = new JButton("삭제");
        removeBookButton.setBounds(815, 0, 100, 100);
        removeBookButton.addActionListener((e) -> {
            try {
                bookController.removeBookById(book.getId());

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "카테고리가 중복되었습니다. 다른 카테고리를 선택하세요.", "경고", JOptionPane.WARNING_MESSAGE);
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
            location = new URL("https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/5298bac0-b8bf-4c80-af67-725c1272dbb0/dd81df5-04364350-282a-441a-ac71-ff7674d70f6d.jpg/v1/fit/w_828,h_466,q_70,strp/aladdin__2019__wallpaper_by_thekingblader995_dd81df5-414w-2x.jpg?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwiaXNzIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsIm9iaiI6W1t7ImhlaWdodCI6Ijw9MTA4MCIsInBhdGgiOiJcL2ZcLzUyOThiYWMwLWI4YmYtNGM4MC1hZjY3LTcyNWMxMjcyZGJiMFwvZGQ4MWRmNS0wNDM2NDM1MC0yODJhLTQ0MWEtYWM3MS1mZjc2NzRkNzBmNmQuanBnIiwid2lkdGgiOiI8PTE5MjAifV1dLCJhdWQiOlsidXJuOnNlcnZpY2U6aW1hZ2Uub3BlcmF0aW9ucyJdfQ.6tu1tC6Ilkb_DdjvsuwbTywPsSgiZnrDwRnogX5rj0Q");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
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
        summaryLabel.setBounds(490,150,715,50);
        add(summaryLabel);

        String description = "<html>"+insertLineBreaks(book.getDescription(), 30) + "</html>";
        JLabel descriptionLabel = new JLabel(description);
        descriptionLabel.setFont(malgunGothic);
        descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        descriptionLabel.setBounds(490,200,715,200);
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
    protected void update(int page) {

    }

    @Override
    protected void init() {
        setLayout(null);
        setSize(contentWidth, contentHeight);
    }
}