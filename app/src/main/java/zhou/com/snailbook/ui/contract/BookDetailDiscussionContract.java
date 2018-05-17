package zhou.com.snailbook.ui.contract;

import java.util.List;

import zhou.com.snailbook.base.BaseContract;
import zhou.com.snailbook.bean.DiscussionList;

/**
 * Created by zhou on 2018/1/26.
 */

public interface BookDetailDiscussionContract {
    interface View extends BaseContract.BaseView {
        void showBookDetailDiscussionList(List<DiscussionList.PostsBean> list, boolean isRefresh);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getBookDetailDiscussionList(String bookId, String sort, int start, int limit);
    }
}
