package com.booklink.ui.panel.content.book.bookdiscussion.comment;

import com.booklink.controller.DiscussionCommentController;
import com.booklink.model.book.disscussion.comment.DiscussionCommentDto;
import com.booklink.ui.frame.main.MainFrame;
import com.booklink.ui.panel.content.ContentPanel;
import com.booklink.ui.panel.content.PagingPanel;

import java.util.List;

public class DiscussionCommentPanel extends ContentPanel {

    private static final int COMMENT_WIDTH = 1215;
    private static final int COMMENT_HEIGHT = 420;

    private DiscussionCommentController commentController;
    private PagingPanel pagingPanel;
    private int currentPage;
    private List<DiscussionCommentDto> comments;
    private Long discussionId;

    public DiscussionCommentPanel(MainFrame mainFrame, Long discussionId) {
        super(mainFrame);
        this.commentController = new DiscussionCommentController();
        // 1240 * 320은 댓글의 내용이고 댓글은 사용자 이름, 작성 날짜, rating을 볼 수가 있다.
        // 그리고 사용자가 구매를 했는지, 안했는지에 따라서 색 표기를 다르게 할 것이다.
        currentPage = 1;
        this.discussionId = discussionId;
        comments = commentController.findAllCommentByDiscussionId(discussionId);
        pagingPanel = new PagingPanel(1600, 100, this);
        updateDisplay(currentPage);
    }

    protected int getMaxPage() {
        return (int) Math.ceil((double)comments.size() / 2);
    }

    protected int getCurrentPage() {
        return currentPage;
    }

    public void updateDisplay(int page) {
        removeAll();
        comments = commentController.findAllDiscCommentByDiscId(discussionId);
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
            DiscussionCommentDto discussionCommentDto = comments.get(i);
            DiscussionCommentSummaryPanel summary = new DiscussionCommentSummaryPanel(discussionCommentDto);
            add(summary);
        }
        // 입력란
        DiscussionCommentInputPanel commentInputPanel = new DiscussionCommentInputPanel(discussionId);
        add(commentInputPanel);
    }

    public void removeComment(Long commentId, Long userId) {
        commentController.removeComment(commentId, userId);
    }
}
