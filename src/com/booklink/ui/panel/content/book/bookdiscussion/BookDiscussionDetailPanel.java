package com.booklink.ui.panel.content.book.bookdiscussion;

import com.booklink.model.book.disscussion.BookDiscussionDto;
import com.booklink.ui.frame.main.MainFrame;
import com.booklink.ui.panel.content.ContentPanel;
import com.booklink.ui.panel.content.book.bookdetail.comment.CommentPanel;
import com.booklink.ui.panel.content.book.bookdiscussion.comment.DiscussionCommentPanel;

import javax.swing.*;
import java.awt.*;

// 1400 * 900
public class BookDiscussionDetailPanel extends ContentPanel {
    public BookDiscussionDetailPanel(MainFrame mainFrame, BookDiscussionDto discussionDto) {
        super(mainFrame);
        init();

        JLabel detailTitleLabel = new JLabel("제목: " + discussionDto.title() + " & 작성자: " + discussionDto.userName());
        detailTitleLabel.setBounds(0, 0, 1400, 100);
        detailTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(detailTitleLabel);

        JTextArea detailContentArea = new JTextArea(discussionDto.content());
        detailContentArea.setBounds(0, 100, 1400, 300);
        detailContentArea.setLineWrap(true);
        detailContentArea.setWrapStyleWord(true);
        detailContentArea.setEditable(false);

        JScrollPane contentScrollPane = new JScrollPane(detailContentArea);
        contentScrollPane.setBounds(0, 100, 1400, 300);
        add(contentScrollPane);


        DiscussionCommentPanel commentPanel = new DiscussionCommentPanel(mainFrame, discussionDto.discussionId());
        commentPanel.setBounds(0, 400, 1215, 400);
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
