package zhou.com.snailbook.ui.contract;

import zhou.com.snailbook.base.BaseContract;
import zhou.com.snailbook.bean.CommentList;
import zhou.com.snailbook.bean.Disscussion;

/**
 * Created by zhou on 2018/1/27.
 */

public interface BookDiscussionDetailContract {

    interface View extends BaseContract.BaseView {

        void showBookDisscussionDetail(Disscussion disscussion);

        void showBestComments(CommentList list);

        void showBookDisscussionComments(CommentList list);

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void getBookDisscussionDetail(String id);

        void getBestComments(String disscussionId);

        void getBookDisscussionComments(String disscussionId, int start, int limit);

    }

}