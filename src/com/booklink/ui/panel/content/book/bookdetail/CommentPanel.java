package com.booklink.ui.panel.content.book.bookdetail;

import com.booklink.ui.frame.main.MainFrame;
import com.booklink.ui.panel.content.ContentPanel;
import com.booklink.ui.panel.content.PagingPanel;

public class CommentPanel extends ContentPanel {

    private static final int COMMENET_WIDTH = 1215;
    private static final int COMMENT_HEIGHT = 420;

    public CommentPanel(MainFrame mainFrame) {
        super(mainFrame);
        // 1240 * 320은 댓글의 내용이고 댓글은 사용자 이름, 작성 날짜, rating을 볼 수가 있다.
        // 그리고 사용자가 구매를 했는지, 안했는지에 따라서 색 표기를 다르게 할 것이다.

        PagingPanel pagingPanel = new PagingPanel(1240, 100, this);
        add(pagingPanel);
    }

    @Override
    protected int getMaxPage() {
        return 0;
    }

    @Override
    protected int getCurrentPage() {
        return 0;
    }

    @Override
    protected void update(int page) {
        // 페이징이 눌리면 댓글 목륵을 전부 가지고 온다.
        // commentDto -> userId, commentId, comment, rating, userName

    }
}
