package zhou.com.snailbook.ui.contract;

import zhou.com.snailbook.base.BaseContract;
import zhou.com.snailbook.bean.BookHelp;
import zhou.com.snailbook.bean.CommentList;

/**
 * Created by zhou on 2018/1/28.
 */

public interface BookHelpDetailContract {

    interface View extends BaseContract.BaseView {

        void showBookHelpDetail(BookHelp data);

        void showBestComments(CommentList list);

        void showBookHelpComments(CommentList list);

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void getBookHelpDetail(String id);

        void getBestComments(String helpId);

        void getBookHelpComments(String helpId, int start, int limit);

    }

}

