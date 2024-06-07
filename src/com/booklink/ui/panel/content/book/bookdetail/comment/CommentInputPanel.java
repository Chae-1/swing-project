package com.booklink.ui.panel.content.book.bookdetail.comment;

import com.booklink.controller.CommentController;
import com.booklink.model.book.comments.CommentFormDto;
import com.booklink.model.user.exception.UserException;
import com.booklink.utils.UserHolder;
import java.awt.Container;
import java.awt.Dimension;
import java.time.LocalDateTime;
import javax.swing.*;

public class CommentInputPanel extends JPanel {

    private CommentController commentController;

    public CommentInputPanel(Long bookId) {
        setLayout(null);
        commentController = new CommentController();
        setPreferredSize(new Dimension(1215, 100));

        // 입력란
        JTextArea inputArea = new JTextArea();
        inputArea.setBounds(0, 0, 850, 100);
        inputArea.setLineWrap(true);
        inputArea.setWrapStyleWord(true);
        add(inputArea);

        // 점수판
        String[] ratings = {"1", "2", "3", "4", "5"};
        JComboBox<String> ratingComboBox = new JComboBox<>(ratings);
        ratingComboBox.setBounds(860, 0, 121, 30);
        add(ratingComboBox);

        // 확인 버튼
        JButton submitButton = new JButton("등록");
        submitButton.setBounds(981, 0, 100, 100);
        // submitButton이 눌리면, JComboBox의 내용,
        // Area의 내용을 가지고 와서
        // 현재 User가 Comment를 남긴다.
        // 단, User가 해당 Comment에 대해 수정은 가능하나, 중복 등록은 허용하지 않는다.
        // 해당 고객의 구매 내역이 있는지 확인한다.
        submitButton.addActionListener((event) -> {
            int rating = Integer.parseInt((String) ratingComboBox.getSelectedItem());
            String text = inputArea.getText();
            try {
                commentController.registerComment(new CommentFormDto(UserHolder.getId(), bookId, rating,
                        LocalDateTime.now(), text));
                Container parent = this.getParent();
                if (parent instanceof CommentPanel p) {
                    p.updateDisplay(1);
                }
            } catch (UserException e) {
                // comment 예외시, 메세지를 출력
                // popUp 형태로 제공할 것.
                JOptionPane.showMessageDialog(this, e.getMessage(),
                        "Fail", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        add(submitButton);
    }


    public static void main(String[] args) {
        int n1 = 1;
        int n2 = 2;
        System.out.println("adsasd");
    }
}
