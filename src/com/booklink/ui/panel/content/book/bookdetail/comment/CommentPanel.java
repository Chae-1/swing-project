package com.booklink.ui.panel.content.book.bookdetail.comment;

import com.booklink.controller.CommentController;
import com.booklink.model.book.comments.CommentDto;
import com.booklink.ui.frame.main.MainFrame;
import com.booklink.ui.panel.content.ContentPanel;
import com.booklink.ui.panel.content.PagingPanel;
import java.util.List;

public class CommentPanel extends ContentPanel {

    private static final int COMMENT_WIDTH = 1215;
    private static final int COMMENT_HEIGHT = 420;

    private CommentController commentController;
    private PagingPanel pagingPanel;
    private int currentPage;
    private List<CommentDto> comments;
    private Long bookId;

    public CommentPanel(MainFrame mainFrame, Long bookId) {
        super(mainFrame);
        this.commentController = new CommentController();
        // 1240 * 320은 댓글의 내용이고 댓글은 사용자 이름, 작성 날짜, rating을 볼 수가 있다.
        // 그리고 사용자가 구매를 했는지, 안했는지에 따라서 색 표기를 다르게 할 것이다.
        currentPage = 1;
        this.bookId = bookId;
        comments = commentController.findAllCommentByBookId(bookId);
        updateComments();
        pagingPanel = new PagingPanel(1215, 100, this);
        add(pagingPanel);
    }

    @Override
    protected int getMaxPage() {
        return (int) Math.ceil((double)comments.size() / 2);
    }

    @Override
    protected int getCurrentPage() {
        return currentPage;
    }

    @Override
    protected void update(int page) {
        // 페이징이 눌리면 댓글 목륵을 전부 가지고 온다.
        // commentDto -> userId, commentId, comment, rating, userName
        removeAll();
        comments = commentController.findAllCommentByBookId(bookId);
        currentPage = page;
        pagingPanel.updatePagingPanel();
        updateComments();
        add(pagingPanel);

        revalidate();
        repaint();
    }

    private void updateComments() {
        int start = (currentPage - 1) * 2;
        int end = Math.min(currentPage * 2, comments.size());
        for (int i = start; i < end; i++) {
            CommentDto commentDto = comments.get(i);
            CommentSummaryPanel summary = new CommentSummaryPanel(commentDto);
            add(summary);
        }
        // 입력란
        CommentInputPanel commentInputPanel = new CommentInputPanel(bookId);
        add(commentInputPanel);
    }

    public void removeComment(Long commentId, Long userId) {
        commentController.removeComment(commentId, userId);
    }
}
