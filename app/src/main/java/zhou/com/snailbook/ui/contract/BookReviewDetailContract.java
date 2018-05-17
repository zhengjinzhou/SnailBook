package zhou.com.snailbook.ui.contract;

import zhou.com.snailbook.base.BaseContract;
import zhou.com.snailbook.bean.BookReview;
import zhou.com.snailbook.bean.CommentList;

/**
 * Created by zhou on 2018/1/27.
 */

public interface BookReviewDetailContract {

    interface View extends BaseContract.BaseView {

        void showBookReviewDetail(BookReview data);

        void showBestComments(CommentList list);

        void showBookReviewComments(CommentList list);

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void getBookReviewDetail(String id);

        void getBestComments(String bookReviewId);

        void getBookReviewComments(String bookReviewId, int start, int limit);

    }

}