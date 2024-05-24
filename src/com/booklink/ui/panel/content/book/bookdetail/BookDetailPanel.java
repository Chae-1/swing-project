package com.booklink.ui.panel.content.book.bookdetail;

import com.booklink.controller.CategoryController;
import com.booklink.model.book.Book;
import com.booklink.ui.frame.main.MainFrame;
import com.booklink.ui.panel.content.ContentPanel;
import com.booklink.ui.panel.content.PagingPanel;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class BookDetailPanel extends ContentPanel {
    private CategoryController controller = new CategoryController();
    public BookDetailPanel(MainFrame mainFrame, Book book) {
        super(mainFrame);
        init();
        JLabel titleLabel = new JLabel(book.getTitle() + ": " + book.getAuthor() + "/" + book.getPublisher());
        titleLabel.setSize(1215, 100);
        titleLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel).setBounds(0, 0, 1215, 100);
        List<String> allCategories = controller.findAllCategories(book.getId());
        for (String allCategory : allCategories) {
            System.out.println(allCategory);
        }
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

        JLabel descriptionLabel = new JLabel(book.getDescription());
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
        JLabel categoriesLabel = new JLabel("학");
        categoriesLabel.setFont(malgunGothic);
        categoriesLabel.setBounds(490, 400, 715, 100);
        add(categoriesLabel);
        // 500

        CommentPanel commentPanel = new CommentPanel(mainFrame);
        add(commentPanel);
    }

    /**
     * emp 행 10개
     *
     * select sum(sal) over(parition by deptno order by sal), sal, deptno
     * from emp;
     *
     * deptno = 10, 20, 30, 40
     */

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